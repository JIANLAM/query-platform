package com.zt.queryplatform.entity;

import javax.persistence.*;

/**
 * 书的实体类
 */
@Entity
@Table(name ="t_book")
public class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pic;//书封面
    private String title;//书名
    private String author;//作者
    private String summary;//概要
    @Column(name = "pub_area")
    private String pubArea;//出版地区
    private String publisher;//出版社
    private String pubdate;//出版日期
    @Column(name = "book_type")
    private String bookType;//书的类目
    private String isbn;//isbn
    private Float price;//单价
    @Column(name = "create_by")
    private Long createBy;//创建者
    @Column(name = "create_time")
    private String createTime;//创建时间
    private String pages;//页数
    private Integer bookrecno;//图创id
    private String rowid;//图创id
    @Column(name = "series_title")
    private String seriesTitle;//从题名
    @Column(name = "second_title")
    private String secondTitle;//副题名
    @Column(name = "subject_word")
    private String subjectWord;//主题词
    @Column(name = "first_duty")
    private String firstDuty;//第一责任人
    private String revision;//版次
    private String language;//语种
    @Column(name = "carrier_type")
    private String carrierType;//载体类型:图书、报刊、录相带、光碟
    private String binding;//装帧类型：平装、精装
    @Column(name = "book_size")
    private String bookSize;//书的开本:32开
    @Column(name = "parallel_title")
    private String parallelTitle;//并列题名
    @Column(name = "call_no")
    private String callNo;//索书号
    private Long ownlib;//图书馆id
    @Column(name = "division_number")
    private String divisionNumber;//分册号
    @Column(name = "other_duty")
    private String otherDuty;//其他负责人
    private String currency;//币种:0 RMB人民币   1USD美元  2JPY日元  3EUR欧元   4HKD港币   5KRW韩国元
    @Column(name = "division_name")
    private String divisionName;//分辑名
    @Column(name = "price_test")
    private String priceTest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPubArea() {
        return pubArea;
    }

    public void setPubArea(String pubArea) {
        this.pubArea = pubArea;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public Integer getBookrecno() {
        return bookrecno;
    }

    public void setBookrecno(Integer bookrecno) {
        this.bookrecno = bookrecno;
    }

    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }

    public String getSecondTitle() {
        return secondTitle;
    }

    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;
    }

    public String getSubjectWord() {
        return subjectWord;
    }

    public void setSubjectWord(String subjectWord) {
        this.subjectWord = subjectWord;
    }

    public String getFirstDuty() {
        return firstDuty;
    }

    public void setFirstDuty(String firstDuty) {
        this.firstDuty = firstDuty;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getBookSize() {
        return bookSize;
    }

    public void setBookSize(String bookSize) {
        this.bookSize = bookSize;
    }

    public String getParallelTitle() {
        return parallelTitle;
    }

    public void setParallelTitle(String parallelTitle) {
        this.parallelTitle = parallelTitle;
    }

    public String getCallNo() {
        return callNo;
    }

    public void setCallNo(String callNo) {
        this.callNo = callNo;
    }

    public Long getOwnlib() {
        return ownlib;
    }

    public void setOwnlib(Long ownlib) {
        this.ownlib = ownlib;
    }

    public String getDivisionNumber() {
        return divisionNumber;
    }

    public void setDivisionNumber(String divisionNumber) {
        this.divisionNumber = divisionNumber;
    }

    public String getOtherDuty() {
        return otherDuty;
    }

    public void setOtherDuty(String otherDuty) {
        this.otherDuty = otherDuty;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getPriceTest() {
        return priceTest;
    }

    public void setPriceTest(String priceTest) {
        this.priceTest = priceTest;
    }
}
