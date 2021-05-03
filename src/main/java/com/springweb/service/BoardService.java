package com.springweb.service;

import com.springweb.web.dto.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.springweb.domain.board.*;
import com.springweb.web.dto.BoardDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    //생성
    @Transactional
    public Long SaveBoard(  BoardDto boardDto  ){
        return boardRepository.save( boardDto.boardEntity()).getBbsID();
    }

    //수정
    @Transactional
    public Long UpdateBoard(Long id , BoardUpdateRequestDto requestDto ){
        // 수정할 대상 찾기 [ 게시물 번호 , 업데이트 내용 ]
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityOptional.get();
        // 못찾았으면 null 예외처리 => optional
        // 찾았으면 업데이트
        boardEntity.update( requestDto.getBbsTitle() , requestDto.getBbsContent()  );
        return id;
    }

    //삭제
    @Transactional
    public void DeleteBoard( Long id){
        // 삭제할 대상 찾기
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityOptional.get();
        // 찾았으면 삭제
        boardRepository.delete( boardEntity );
    }
    //전체조회
    @Transactional
    public List<BoardDto> getAllBoard(){
        // 1. 모든 엔티티 가져오기
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        // 2. 반복문을 사용하여 검색된 엔티티를 dto 담아서 리스트에 담기
        for( BoardEntity boardEntity : boardEntities ){ // 모든 엔티티 만큼 반복
            BoardDto boardDto = BoardDto.builder()
                    .bbsID( boardEntity.getBbsID() )
                    .bbsTitle( boardEntity.getBbsTitle() )
                    .bbsCategory(boardEntity.getBbsCategory())
                    .bbsContent(boardEntity.getBbsContent())
                    .bbsReply(boardEntity.getBbsReply())
                    .userID(boardEntity.getUserID())
                    .createdDate(boardEntity.getCreateDate())
                    .modifiedDate(boardEntity.getModifiedDate())
                    .build();
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    //개별조회(조건조회)
    @Transactional
    public BoardDto getBoard( Long id ){
        // 검색할 대상 찾기
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityOptional.get();
        // 찾았으면 dto 담기
        BoardDto boardDto = BoardDto.builder()
                .bbsID( boardEntity.getBbsID() )
                .bbsTitle( boardEntity.getBbsTitle() )
                .bbsCategory(boardEntity.getBbsCategory())
                .bbsContent(boardEntity.getBbsContent())
                .bbsReply(boardEntity.getBbsReply())
                .userID(boardEntity.getUserID())
                .createdDate(boardEntity.getCreateDate())
                .modifiedDate(boardEntity.getModifiedDate())
                .build();
        return boardDto;
    }
}
