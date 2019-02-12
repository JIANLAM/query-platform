package com.zt.queryplatform.service.impl;

import com.zt.queryplatform.base.BorrowBookStatus;
import com.zt.queryplatform.base.DateUtils;
import com.zt.queryplatform.base.IdGen;
import com.zt.queryplatform.entity.*;
import com.zt.queryplatform.entity.dto.LendDTO;
import com.zt.queryplatform.entity.dto.ReaderInfoDTO;
import com.zt.queryplatform.entity.dto.RuleDTO;
import com.zt.queryplatform.repository.*;
import com.zt.queryplatform.service.ReaderService;
import com.zt.queryplatform.service.common.ServiceResult;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * created by linzj on 2018/12/21
 **/
@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReadercardRepository readercardRepository;

    @Autowired
    private LendRuleRepository lendRuleRepository;

    @Autowired
    private LendRepository lendRepository;

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private  HoldingRepository holdingRepository;

    @Autowired
    private RelendRepository relendRepository;

    @Autowired
    private BookRepository bookRepository;


    @Override
    public ServiceResult<ReaderInfoDTO> getReaderInfo(Long userId, Long library) {

        Reader reader = getReaderObeject(userId,library);
        if(reader == null ||  reader.getStatus()==null || reader.getStatus()!=1){
            return ServiceResult.notFound();
        }
        //填充读者信息详情实体
        ReaderInfoDTO readerInfo = modelMapper.map(reader,ReaderInfoDTO.class);
        readerInfo.setUserId(userId);

        //设置名字
        People people = peopleRepository.findPeopleByUserId(userId);
        readerInfo.setReaderName(people.getUsername());
        readerInfo.setIcon(people.getIcon());

        //设置证件状态名
        switch (readerInfo.getStatus().intValue()){
            case 0:
                readerInfo.setStartusName("禁用");break;
            case 1:
                readerInfo.setStartusName("正常");break;
            case 2:
                readerInfo.setStartusName("注销");break;
            case 3:
                readerInfo.setStartusName("挂失");break;
                default:
                    break;
        }
        //证类型 -> 查询读者证表
        ReaderCard readerCard = readercardRepository.findOne(reader.getReaderType());
        if(readerCard != null){
            readerInfo.setReaderTypeName(readerCard.getLabel());
        }
        //预付款
        readerInfo.setPrePayment(reader.getPrePayment());
        //借阅次数 -> 查询借阅规则
        LendRule rule = lendRuleRepository.findOne(readerCard.getMuseumRule());

        if(rule == null || rule.getLendQty()<0){
           readerInfo.setLendNum(0);
        }
        //可借阅次数
        readerInfo.setLendNum(rule.getLendQty());
        readerInfo.setLendRule(rule);

        //统计借阅次数,查询状态不包含已还的
        List<Lend> lend = lendRepository.getAllByLendLibIdAndReaderIdOrderByLendTimeDesc(library, reader.getId());

        List<LendDTO> lendDTOList = new ArrayList<>();
        if(lend  == null){
          readerInfo.setBorrow(0);
        }else{
            List<Lend> lendListIng = lendRepository.getAllByLendLibIdAndReaderIdAndLendStatus(library, reader.getId(), 0);
            readerInfo.setBorrow(lendListIng.size());
            lend.forEach(lendvo -> {
               LendDTO lendDTO =  modelMapper.map(lendvo,LendDTO.class);
               //获取条码号
                Holding holding = holdingRepository.findHoldingById(lendDTO.getHoldingId());
                if(holding !=null){
                    lendDTO.setBarcode(holding.getBarcode());
                    Book book = bookRepository.findOne(holding.getBookId());
                    lendDTO.setBook(book);

                    switch (lendvo.getLendStatus()){
                        case 0:
                            lendDTO.setLendStatusName("在借");break;
                        case 1:
                            lendDTO.setLendStatusName("已还");break;
                        case 2:
                            lendDTO.setLendStatusName("续借");break;
                        case 3:
                            lendDTO.setLendStatusName("逾期");break;
                        default:
                            break;
                    }
                }
                lendDTOList.add(lendDTO);
            });
        }

        readerInfo.setLendBookList(lendDTOList);
        return ServiceResult.of(readerInfo);
    }

    @Override
    @Transactional
    public ServiceResult renewBookOperation(Long userId,Long libraryId,Long holdingId,Long lendId) {

        ServiceResult<ReaderInfoDTO> readerInfo = getReaderInfo(userId, libraryId);

        if(readerInfo == null){
            return ServiceResult.notFound();
        }
        ReaderInfoDTO readerInfoDTO = readerInfo.getResult();

        //校验规则
        if(readerInfoDTO == null || !CheckIsFixToRule(readerInfoDTO,holdingId,lendId)){
            return ServiceResult.notFound();
        }

        excuteRenewOpertion(readerInfoDTO,libraryId,lendId);
        return ServiceResult.success();
    }


    /**
     * 获取读者对象的方法
     */
    private Reader getReaderObeject(Long userId,Long libraryId){
        People people = peopleRepository.findPeopleByUserId(userId);
        if(people == null){
            return null;
        }
       return readerRepository.findReaderByPeopleIdAndLibraryId(people.getId(),libraryId);
    }


    /**
     * 校验是否符合借阅规则
     */
    private  Boolean CheckIsFixToRule(ReaderInfoDTO readerInfoDTO,Long holdingId,Long lendId){
        String literatureType = "";
        //获取借阅规则
        LendRule rule = readerInfoDTO.getLendRule();
        //获取此读者已经借阅了几本书
        Integer lendQty = readerInfoDTO.getBorrow();
        lendQty++;
        //获取 读者当前信用分
        Credit creditByUserId = creditRepository.findCreditByUserId(readerInfoDTO.getUserId());
        Integer credit = creditByUserId.getOwnValue();

        Holding holding = holdingRepository.findHoldingById(holdingId);
        //获取文献类型
        if(holding!= null){
            literatureType = holding.getActType();
        }
        //获取此读者已经续借了几次
        //fixme 续借次数
        Integer reLendQty =relendRepository.countByLendId(lendId);
        reLendQty++;
        // 获取预付款金额
        Double prePayment = readerInfoDTO.getPrePayment();
        if(lendQty == null){
            lendQty = 0;
        }
        if(credit == null){
            credit = 0;
        }
        if(reLendQty == null){
            reLendQty = 0;
        }
        if(lendQty > rule.getLendQty() || prePayment < 0){
            //读者已经借阅的图书册数加上当前借阅数量 大于 规定册数 失败
            //读者当前预付款金额 为负数 不允许借阅
           return false;
        }
        if( reLendQty> rule.getRenewQty()){
            //续借次数为空 则不限制 不为空 读者已经续借的图书的次数加上当前续借一次数量 大于 规定续借次数 失败
            return false;
        }
        if(rule.getCredit()!=null){
            //读者当前信用分小于规则 信用分 失败
            if( credit < rule.getCredit()){
                return false;
            }
        }
        //判断此读者 读者证类型是否能够借阅此文献类型的图书
        String [] collectionIdStr = (rule.getLiteratureType() == null ? "" : rule.getLiteratureType()).split(",");
        boolean flg = false;
        for (int i =0 ; i < collectionIdStr.length ; i++){
            if(literatureType.equals(collectionIdStr[i])){
                flg = true;
            }
        }
        return flg;
    }

    /**
     * 执行续借操作
     */
    public void excuteRenewOpertion(ReaderInfoDTO readerInfoDTO,Long libraryId,Long lendId){

        //设置日期格式
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //借阅实体
        Lend lend = lendRepository.findOne(lendId);
        LendDTO lendDTO = modelMapper.map(lend,LendDTO.class);
        //规则实体
        LendRule lendRule = readerInfoDTO.getLendRule();
        RuleDTO ruleDTO = modelMapper.map(lendRule,RuleDTO.class);

        String primaryBackTime = lendDTO.getDueBackTime();
        //是否续借过，0 否；1 是
        int renew = 1;
        //借阅状态0在借，1已还，2续借，3逾期
        int lend_status=BorrowBookStatus.IS_BORROW_AGAIN.getValue();
        //设置 续借规则 默认可续借天数为 30 天
        int reLendDays = 30;
        //存储 转换格式的 时间日期 yyyy-MM-dd HH:mm:ss
        String time;
        Date date = new Date();
        if (ruleDTO.getRenewDays() == null || ruleDTO.getRenewDays() == 0) {
            //续借应归还日期   默认借阅天数
            time = sdf.format(DateUtils.getDateToN(new Date(), reLendDays));
        } else {
            //续借应归还日期
            time = sdf.format(DateUtils.getDateToN(new Date(), ruleDTO.getRenewDays()));
        }
        try {
             date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        lend.setLendLibId(libraryId);
        //修改借阅 状态 为续借
        lendRepository.updateLend(lendId,renew,lend_status,date);
        //新增续借信息
        Relend relend = new Relend();
        relend.setId(IdGen.randomLong());
        relend.setLendId(lend.getId());
        //原应还书日期
        relend.setPrimaryBackTime(primaryBackTime);
        //续借归还 日期
        relend.setRelendBackTime(time);
        //续借日期
        relend.setRelendTime(DateUtils.getDateTime());
        relendRepository.save(relend);
    }
}
