package com.zt.queryplatform.service.impl;

import com.zt.queryplatform.base.DateUtils;
import com.zt.queryplatform.base.IdGen;
import com.zt.queryplatform.base.LoginUserUtil;
import com.zt.queryplatform.entity.*;
import com.zt.queryplatform.entity.dto.CollectionDTO;
import com.zt.queryplatform.repository.*;
import com.zt.queryplatform.service.CollectionService;
import com.zt.queryplatform.service.common.ServiceResult;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

/**
 * created by linzj on 2018/12/25
 **/

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PreBookRepository preBookRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private  PeopleRepository peopleRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    @Transactional
    public ServiceResult addToMyCollection(Long libraryId,Long bookId) {

        Long userId = LoginUserUtil.getLoginUserId();
        if(userId<0){
            return ServiceResult.notLogin();
        }
        User user = userRepository.findOne(userId);

        People people = peopleRepository.findPeopleByUserId(user.getId());

        if(userId<0){
            return ServiceResult.notFound();
        }
        Reader reader = readerRepository.findReaderByPeopleIdAndLibraryId(people.getId(),libraryId);

        if(reader == null){
            return ServiceResult.notFound();
        }

        CollectionDTO collectionDTO = new CollectionDTO();
        collectionDTO.setReaderId(reader.getId());
        collectionDTO.setUserId(userId);
        //添加到采访库中
        Prebook prebook = new Prebook();
        //主键
        prebook.setId(IdGen.randomLong());
        //0采购，1借购，2荐购，3收藏
        prebook.setInType(3);
        //0未购买，状态1购买中，2已入藏
        prebook.setStatus(0);
        //创建者
        prebook.setCreateBy(userId);
        //创建时间
        prebook.setCreateTime(DateUtils.getDateTime());
        //入库时间
        prebook.setStorageTime(DateUtils.getDateTime());
        //图书馆
        prebook.setOwnlib(libraryId);

        Book book = bookRepository.findOne(bookId);
        List<Prebook> prebookList =new ArrayList<>();
        if(book !=null){
            prebook.setPic(book.getPic());
            prebook.setTitle(book.getTitle());
            prebook.setAuthor(book.getAuthor());
            prebook.setPubdate(book.getPubdate());
            prebook.setPublisher(book.getPublisher());
            prebook.setIsbn(book.getIsbn());
            prebook.setSummary(book.getSummary());
            prebook.setBookType(book.getBookType());
            prebook.setPrice(book.getPrice());
            prebook.setPages(book.getPages());
            prebook.setBookSize(book.getBookSize());
            //1新华集团2本馆图书3豆瓣图书4成员馆图书
            prebook.setSource(2);
        }
        if(prebook.getIsbn()!=null  && prebook.getTitle()!=null){
            prebookList = preBookRepository.findAllByIsbnAndTitle(prebook.getIsbn(), prebook.getTitle());
        }
        List<Long>  idList = new ArrayList<>();
        if(prebookList.size()==0){
            preBookRepository.save(prebook);
            collectionDTO.setPreBookId(prebook.getId());
        }else{
            collectionDTO.setPreBookId(prebookList.get(0).getId());
            //填充prebookid 集合
            prebookList.forEach(pb -> {
                idList.add(pb.getId());
            });
        }
        collectionDTO.setCreateTime(DateUtils.getDateTime());

        List<Collection> collectionList = collectionRepository.findAllByPreBookIdIn(idList);

        if(collectionList.size() == 0){
          collectionDTO.setId(IdGen.randomLong());
            Collection c =modelMapper.map(collectionDTO,Collection.class);
            c.setCollectFrom(0);
            collectionRepository.save(c);
        }
        return ServiceResult.success();
    }
}
