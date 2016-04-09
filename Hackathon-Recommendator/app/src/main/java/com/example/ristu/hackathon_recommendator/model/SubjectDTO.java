package com.example.ristu.hackathon_recommendator.model;

/**
 * Created by ristu on 4/9/2016.
 */
public class SubjectDTO {
    public String id;
    public String name;
    public String numberclass;
    public String startcourse;
    public String endcourse;
    public String category;
    public String description;

    public boolean isRegister;

    public SubjectDTO(String id, String name, String numberclass, String startcourse, String endcourse, String category, String description) {
        this.id = id;
        this.name = name;
        this.numberclass = "Số buổi học: " + numberclass;
        this.startcourse = "Từ " + startcourse;
        this.endcourse = "Đến " + endcourse;
        this.category = "Nhóm ngành " + category;
        this.description = description;
    }
    public SubjectDTO (String id, String name) {
        this.id = id;
        this.name = name;
        this.numberclass = "Số buổi học: " +"13";
        this.startcourse = "Từ ngày: " +"2016-04-09";
        this.endcourse = "Đến ngày: " + "2016-05-10";
        this.category = "Nhóm ngành: " + "1";
        this.description = "Dấu câu tiếng Anh làm bạn đau đầu ư? Thế thì khóa học này thật sự cần thiết cho bạn!Bạn đã phát ốm vì mọi người bảo bài viết của bạn như của một đứa trẻ? Bạn biết chính xác muốn diễn đạt cái gì, nhưng dấu câu lại khiến bạn bất lực? Dấu câu là lí do khiến bạn sự hãi khi mọi người đọc những gì bạn viết ra?Bạn đừng lo vì có rất nhiều người cũng giống như bạn. Và thực tế là, dấu câu tiếng Anh dễ kinh khủng!Chỉ trong vòng 30 phút, tôi có thể dạy bạn tất cả mọi thứ bạn cần để có thể đặt các dấu câu đúng chỗ của nó một cách hoàn hảo. Hãy kiếm soát những lí lẽ của mình, hãy làm bạn trở nên thuyết phục hơn lúc nào hết và hãy làm cho việc đọc các bài viết của bạn trở nên thú vị. ĐỪNG BAO GIỜ để ai cười vào cách sử dụng dấu câu của bạn một lần nữa!Trong khóa học này, mỗi bài học - mỗi đoạn videos rất ngắn gọn và súc tích - sẽ đi sâu và từng dấu câu và bạn sẽ cảm thấy thật thoải mái khi dần dần có thể kiểm soát mọi thứ mà bạn viết.Không có những từ ngữ chuyên môn, không có những bài học về ngữ pháp, đơn giản chỉ là các nguyên tắc đơn giản như bảng chữ cái. Xuyên suốt khóa học tôi sẽ dạy bạn làm sao để lái một chiếc xe mà không phải tháo tung ra mà nghiên cứu. Và tôi sẽ cố truyền tải tất cả nội dung này với tất cả sự hài hước mà tôi có.";
    }
}
