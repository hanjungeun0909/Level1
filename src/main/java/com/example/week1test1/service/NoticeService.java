package com.example.week1test1.service;

import com.example.week1test1.dto.NoticeRequestDto;
import com.example.week1test1.dto.NoticeResponseDto;
import com.example.week1test1.entity.Notice;
import com.example.week1test1.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;


    public List<NoticeResponseDto> getNotices() {
        return noticeRepository.findAllByOrderByCreatedAtDesc().stream().map(NoticeResponseDto::new).toList();
    }

    public List<NoticeResponseDto> getIdNotice(Long id) {
        return noticeRepository.findAllByOrderByCreatedAtDesc().stream().filter(n -> n.getId() == id).map(NoticeResponseDto::new).toList();
    }

    public NoticeResponseDto createNotice(NoticeRequestDto requestDto) {
        // RequestDto -> Entity
        Notice notice = new Notice(requestDto);

        Notice saveNotice = noticeRepository.save(notice);

        // Entity -> ResponseDto
        NoticeResponseDto noticeResponseDto = new NoticeResponseDto(saveNotice);
        return noticeResponseDto;
    }

    @Transactional
    public Notice updateNotice(Long id, NoticeRequestDto requestDto) {
        // 해당 게시글이 DB에 존재하는지 확인
        Notice notice = findNotice(id);

        if(checkPassWord(notice, requestDto.getPassword())){
            notice.update(requestDto);
        }
//        NoticeResponseDto noticeResponseDto = new NoticeResponseDto(no)
        return  notice;
    }


    public String deleteNotice(Long id, String password) {
        // 해당 게시글이 DB에 존재하는지 확인
        Notice notice = findNotice(id);
        if(checkPassWord(notice, password)){
            noticeRepository.delete(notice);
        }
        return "f";
    }
    private boolean checkPassWord(Notice notice, String getpassword){
        if(notice.getPassword().equals(getpassword)){
            return true;
        }
        else{
            throw new IllegalArgumentException("패스워드 일치하지 않음.");
        }
    }


    private Notice findNotice(Long id) {
        return noticeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }

}