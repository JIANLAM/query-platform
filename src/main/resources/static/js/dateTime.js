/**
 * Created by admin on 2018/12/20.
 */
$(function () {
    var header_date=$(".header_date");
    var week=$(".week");
    var time=$(".time");
    var date=new  Date();
    var year=date.getFullYear();
    var month=date.getMonth()+1;
    var day=date.getDate();
    var nowDate=year+"年"+getTwo(month)+"月"+getTwo(day)+"日";
    var nowWeek='周'+'日一二三四五六'.charAt(date.getDay());
    header_date.html(nowDate);
    week.html(nowWeek);
    // 没到10就前面补0
   function getTwo(obj) {
       return obj<10?"0"+obj:obj
   }
   //每秒都获取时间
    function getTime() {
        var date2=new Date();
        var nowHour=date2.getHours();
        var nowMin=date2.getMinutes();
        var nowS=date2.getSeconds();
        var nowTime=getTwo(nowHour)+":"+getTwo(nowMin)+":"+getTwo(nowS);
        time.html(nowTime)
    }
    getTime();
    setInterval(getTime,1000)
})