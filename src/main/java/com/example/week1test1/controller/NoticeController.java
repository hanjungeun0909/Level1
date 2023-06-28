package com.example.week1test1.controller;

import com.example.week1test1.dto.NoticeRequestDto;
import com.example.week1test1.dto.NoticeResponseDto;
import com.example.week1test1.entity.Notice;
import com.example.week1test1.repository.NoticeRepository;
import com.example.week1test1.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

//    public NoticeController(NoticeService noticeService) {
//        this.noticeService = noticeService;
//    }

    // GET
    // http://localhost:8080/api/notices
    @GetMapping("/notices")
    public List<NoticeResponseDto> getNotices() {
        return noticeService.getNotices();
    }
    // GET
    // http://localhost:8080/api/notice/{id}
    @GetMapping("/notice/{id}")
    public List<NoticeResponseDto> getIdNotice(@PathVariable Long id) {
        return noticeService.getIdNotice(id);
    }
    // POST
    // http://localhost:8080/api/notice
    @PostMapping("/notice")
    public NoticeResponseDto createNotice(@RequestBody NoticeRequestDto requestDto) {
        return noticeService.createNotice(requestDto);
    }
    // PUT
    // http://localhost:8080/api/notice/{id}
    @PutMapping("/notice/{id}")
    public Notice updateNotice(@PathVariable Long id, @RequestBody NoticeRequestDto requestDto){
        return noticeService.updateNotice(id, requestDto);
    }
    // DELETE
    // http://localhost:8080/api/notice/{id}
    @DeleteMapping("/notice/{id}")
    public String deleteNotice(@PathVariable Long id, @RequestBody NoticeRequestDto requestDto){
        return noticeService.deleteNotice(id, requestDto.getPassword());
    }
}
