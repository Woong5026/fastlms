package com.zerobase.fastlms.course.model;

import com.zerobase.fastlms.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    Long id;
    long categoryId;
    String imagePath;
    String keyword;
    String subject;
    String summary;
    String contents;
    long price;
    long salePrice;
    LocalDate saleEndDt;
    LocalDateTime regDt;//등록일(추가날짜)
    LocalDateTime udtDt;//수정일(수정날짜)

    // 페이징 카운트트
    long totalCount;
    long seq;

    String filename;
    String urlFilenames;

    public static CourseDto of(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .categoryId(course.getCategoryId())
                .imagePath(course.getImagePath())
                .keyword(course.getKeyword())
                .subject(course.getSubject())
                .summary(course.getSummary())
                .contents(course.getContents())
                .price(course.getPrice())
                .salePrice(course.getSalePrice())
                .saleEndDt(course.getSaleEndDt())
                .regDt(course.getRegDt())
                .filename(course.getFilename())
                .urlFilenames(course.getUrlFilenames())
                .build();
    }

    public static List<CourseDto> of(List<Course> courses){

        if(courses != null){
            List<CourseDto> courseDtoList = new ArrayList<>();
            for (Course x : courses) {
                // 위에 있는 of 메서드를 호출하고 하나씩 추가한다
                courseDtoList.add(CourseDto.of(x));
            }
            return courseDtoList;
        }
        return null;
    }
}
