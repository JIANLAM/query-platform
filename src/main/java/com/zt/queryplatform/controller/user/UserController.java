package com.zt.queryplatform.controller.user;

import com.zt.queryplatform.base.ApiResponse;
import com.zt.queryplatform.base.LoginUserUtil;
import com.zt.queryplatform.entity.dto.ReaderInfoDTO;
import com.zt.queryplatform.service.CollectionService;
import com.zt.queryplatform.service.ReaderService;
import com.zt.queryplatform.service.common.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * created by linzj on 2018/12/18
 * 用户控制层
 **/

@Controller
public class UserController {

    @Autowired
    private ReaderService readerService;

    @Autowired
    private CollectionService collectionService;

    @Value("${querymachine.libraryId}")
    private  Long libraryId;
    /**
     * 用户登录入口
     * @return
     */
    @GetMapping("/user/login")
    public  String login(){
        return "user/login";
    }

    /**
     * 进入我的图书馆
     */
    @GetMapping("/user/center")
    public String centerPage() {
        return "user/center";
    }


    /**
     * 我的图书馆-获取读者个人信息
     * @return
     */
    @GetMapping("/api/user/getReaderInfoDetail")
    @ResponseBody
    public ApiResponse getReaderInfoDetail(){
        Long userId = LoginUserUtil.getLoginUserId();

        ServiceResult<ReaderInfoDTO> readerInfo = readerService.getReaderInfo(userId, libraryId);

        if(readerInfo == null){
            return ApiResponse.ofMessage(ApiResponse.Status.BAD_REQUEST.getCode(),"该读者证异常，请联系管理员！");
        }
        return ApiResponse.ofSuccess(readerInfo);
    }


    /**
     * 续借操作
     * @param holdingId
     * @param lendId
     * @return
     */
    @GetMapping("/api/user/renewBook")
    @ResponseBody
    public ApiResponse  renewBook(@RequestParam("holdingId")Long holdingId,
                                  @RequestParam("lendId")Long lendId){

        Long userId = LoginUserUtil.getLoginUserId();

        if(lendId <= 0 || holdingId <= 0){
            return ApiResponse.ofMessage(ApiResponse.Status.BAD_REQUEST.getCode(),"传入的id为空！");
        }
        ServiceResult result = readerService.renewBookOperation(userId, libraryId,holdingId,lendId);
        if(result.isSuccess()){
            return ApiResponse.ofSuccess(null);
        }
        return ApiResponse.ofMessage(ApiResponse.Status.BAD_REQUEST.getCode(),"不满足规则，续借操作失败！");
    }

    /**
     * 加入收藏
     * @param bookId
     * @return
     */
    @GetMapping("/api/user/addToMyCollection")
    @ResponseBody
    public  ApiResponse addToMyCollection(@RequestParam("bookId")Long bookId){
        if( bookId<=0){
            return ApiResponse.ofMessage(ApiResponse.Status.BAD_REQUEST.getCode(),"传入的id为空！");
        }
        ServiceResult result = collectionService.addToMyCollection(libraryId, bookId);

        if(result.isSuccess()){
            return ApiResponse.ofSuccess(null);
        }
        return ApiResponse.ofMessage(ApiResponse.Status.BAD_REQUEST.getCode(),"加入收藏操作失败！");
    }
}
