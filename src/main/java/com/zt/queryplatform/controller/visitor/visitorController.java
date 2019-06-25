package com.zt.queryplatform.controller.visitor;

import com.zt.queryplatform.base.ApiResponse;
import com.zt.queryplatform.entity.Book;
import com.zt.queryplatform.entity.LibNews;
import com.zt.queryplatform.entity.Organization;
import com.zt.queryplatform.entity.User;
import com.zt.queryplatform.entity.dto.BookDTO;
import com.zt.queryplatform.entity.dto.LendDTO;
import com.zt.queryplatform.entity.dto.LendOrderDTO;
import com.zt.queryplatform.entity.dto.RecommendedBookDTO;
import com.zt.queryplatform.service.*;
import com.zt.queryplatform.service.common.ServiceMultiResult;
import com.zt.queryplatform.service.common.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * created by linzj on 2018/12/20
 * 未登录的访客操作控制层
 **/

@Controller
public class visitorController {

    @Autowired
    private BookService bookService;

    @Autowired
    private RecommService recommService;

    @Autowired
    private LibNewsService libNewsService;

    @Autowired
    private LendService lendService;

    @Autowired
    private OrginazationService orginazationService;

    @Value("${querymachine.libraryId}")
    private  Long libraryId;

    @GetMapping("/demo")
    @ResponseBody
    public void demo(User user){
        System.out.println(user.toString());
    }

    /**
     * 获取图书列表
     * @param start
     * @param size
     * @return
     */
    @GetMapping("/visitor/getBookList")
    @ResponseBody
    public  ApiResponse getBookList(@RequestParam(name = "start",defaultValue = "0")int start,
                                    @RequestParam(name = "size",defaultValue = "9")int size){

        if(libraryId <= 0){
            return  ApiResponse.ofMessage(ApiResponse.Status.FORBIDDEN.getCode(),"读取信息错误，无权限操作，请联系管理员！");
        }

        ServiceMultiResult<BookDTO> bookList = bookService.getBookList(libraryId, start,size);

        if(bookList.getResultSize() == 0){
            return ApiResponse.ofSuccess(ApiResponse.Status.NOT_VALID_PARAM);
        }
        return ApiResponse.ofSuccess(bookList);
    }


    /**
     * 图书查询
     */
    @GetMapping("/visitor/queryBookList")
    @ResponseBody
    public  ApiResponse queryBookList(@RequestParam(name = "title")String title,
                                      @RequestParam(name = "isbn")String isbn,
                                      @RequestParam(name = "callNo")String callNo,
                                      @RequestParam(name = "author")String author,
                                      @RequestParam(name = "start",defaultValue = "0")int start,
                                      @RequestParam(name = "size",defaultValue = "9")int size){
        if(libraryId <= 0){
            return ApiResponse.ofMessage(ApiResponse.Status.FORBIDDEN.getCode(),"读取信息错误，无权限操作，请联系管理员！");
        }
        //分页参数设置
        Pageable pageable = new PageRequest(start,size,new Sort(Sort.Direction.DESC,"pubdate"));
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setCallNo(callNo);
        book.setAuthor(author);
        book.setOwnlib(libraryId);
        //查询书
        ServiceMultiResult<BookDTO> bookList = bookService.queryBookList(book, pageable,start);

        if(bookList.getResultSize() == 0){
            return ApiResponse.ofSuccess(ApiResponse.Status.NOT_VALID_PARAM);
        }
        return ApiResponse.ofSuccess(bookList);
    }


    /**
     * 获取图书详情
     * @param bookId
     * @return
     */
    @GetMapping("/visitor/getBookDetailInfo")
    @ResponseBody
    public ApiResponse getBookDetailInfo(@RequestParam("bookId")Long bookId){
        if(libraryId <= 0){
            return  ApiResponse.ofMessage(ApiResponse.Status.FORBIDDEN.getCode(),"读取信息错误，无权限操作，请联系管理员！");
        }
        ServiceResult<BookDTO> bookInfoDetail = bookService.getBookInfoDetail(libraryId, bookId);
        return ApiResponse.ofSuccess(bookInfoDetail);
    }


    /**
     * 图书推荐
     * @return
     */
    @GetMapping("/visitor/userRecommBook")
    @ResponseBody
    public  ApiResponse  getBookFromUserRecomm(){
        if(libraryId <= 0){
            return   ApiResponse.ofMessage(ApiResponse.Status.FORBIDDEN.getCode(),"读取信息错误，无权限操作，请联系管理员！");
        }

        //分页参数设置
        Pageable pageable = new PageRequest(0,9,new Sort(Sort.Direction.DESC,"createTime"));
        ServiceMultiResult<BookDTO> recommendedBookList = recommService.findRecommendedBookList(libraryId,pageable);
        if(recommendedBookList.getResultSize() == 0){
            return ApiResponse.ofMessage(ApiResponse.Status.NOT_VALID_PARAM.getCode(),"无相关数据");
        }
        return ApiResponse.ofSuccess(recommendedBookList);
    }


    /**
     * 获取馆内资讯列表
     * @return
     */
    @GetMapping("/visitor/getlibraryNewsList")
    @ResponseBody
    public ApiResponse getLibraryNews(){
        if(libraryId <= 0){
            return   ApiResponse.ofMessage(ApiResponse.Status.FORBIDDEN.getCode(),"读取信息错误，无权限操作，请联系管理员！");
        }
        ServiceMultiResult<LibNews> libNewsList = libNewsService.getLibNewsList(libraryId);

        if(libNewsList.getResultSize() == 0){
            return ApiResponse.ofMessage(ApiResponse.Status.NOT_VALID_PARAM.getCode(),"无相关数据");
        }
        return ApiResponse.ofSuccess(libNewsList);
    }


    /**
     * 获取馆内资讯详情
     * @param id
     * @return
     */
    @GetMapping("/visitor/getlibraryNewsDetail")
    @ResponseBody
    public ApiResponse getlibraryNewsDetail(@RequestParam("id")Long id){
        if(libraryId <= 0){
            return   ApiResponse.ofMessage(ApiResponse.Status.FORBIDDEN.getCode(),"读取信息错误，无权限操作，请联系管理员！");
        }

        ServiceResult<LibNews> libNewsDetail = libNewsService.getLibNewsDetail(id);
        return ApiResponse.ofSuccess(libNewsDetail);
    }

    /**
     * 排行榜 - 图书信息排行
     */
    @GetMapping("/visitor/rankingListOfBook")
    @ResponseBody
    public  ApiResponse rankingListOfBook(){
        if(libraryId <= 0){
            return   ApiResponse.ofMessage(ApiResponse.Status.FORBIDDEN.getCode(),"读取信息错误，无权限操作，请联系管理员！");
        }
        ServiceMultiResult<LendOrderDTO> rankingListOfBook = null;
        try {
            rankingListOfBook = lendService.getRankingListOfBook(libraryId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(rankingListOfBook.getResultSize() == 0){
            return ApiResponse.ofMessage(ApiResponse.Status.NOT_VALID_PARAM.getCode(),"无相关数据");
        }
        return ApiResponse.ofSuccess(rankingListOfBook);
    }


    /**
     * 排行榜 - 读者信息排行
     */
    @GetMapping("/visitor/rankingListOfReader")
    @ResponseBody
    public  ApiResponse rankingListOfReader(){
        if(libraryId <= 0){
            return   ApiResponse.ofMessage(ApiResponse.Status.FORBIDDEN.getCode(),"读取信息错误，无权限操作，请联系管理员！");
        }
        ServiceMultiResult<LendDTO> rankingListOfreader = lendService.getRankingListOfReader(libraryId);

        if(rankingListOfreader.getResultSize() == 0){
            return ApiResponse.ofMessage(ApiResponse.Status.NOT_VALID_PARAM.getCode(),"无相关数据");
        }
        return ApiResponse.ofSuccess(rankingListOfreader);
    }


}
