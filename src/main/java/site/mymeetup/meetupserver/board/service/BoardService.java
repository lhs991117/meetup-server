package site.mymeetup.meetupserver.board.service;

import org.springframework.web.multipart.MultipartFile;
import site.mymeetup.meetupserver.board.dto.CommentDto;
import site.mymeetup.meetupserver.member.dto.CustomUserDetails;

import static site.mymeetup.meetupserver.board.dto.BoardDto.BoardSaveRespDto;
import static site.mymeetup.meetupserver.board.dto.BoardDto.BoardRespDto;
import static site.mymeetup.meetupserver.board.dto.BoardDto.BoardSaveReqDto;
import static site.mymeetup.meetupserver.board.dto.CommentDto.CommentSaveRespDto;
import static site.mymeetup.meetupserver.board.dto.CommentDto.CommentSaveReqDto;
import static site.mymeetup.meetupserver.board.dto.CommentDto.CommentRespDto;

import java.util.List;
import java.util.Map;

public interface BoardService {

    BoardSaveRespDto createBoard(Long crewId, BoardSaveReqDto boardSaveReqDto, CustomUserDetails userDetails);

    List<String> uploadImage(MultipartFile[] images);

    BoardSaveRespDto updateBoard(Long crewId, Long boardId, BoardSaveReqDto boardSaveReqDto, CustomUserDetails userDetails);

    List<BoardRespDto> getBoardByCrewId(Long crewId, String category, int page);

    BoardRespDto getBoardByBoardId(Long crewId, Long boardId, CustomUserDetails userDetails);

    void deleteBoard(Long crewId, Long boardId, CustomUserDetails userDetails);

    CommentSaveRespDto createComment(Long crewId, Long boardId, CommentSaveReqDto commentSaveReqDto, CustomUserDetails userDetails);

    CommentSaveRespDto updateComment(Long crewId, Long boardId, Long commentId, CommentSaveReqDto commentSaveReqDto, CustomUserDetails userDetails);

    void deleteComment(Long crewId, Long boardId, Long commentId, CustomUserDetails userDetails);

    List<CommentRespDto> getCommentByBoardId(Long crewId, Long boardId, CustomUserDetails userDetails, int page);

    BoardSaveRespDto updateBoardStatus(Long crewId, Long boardId, CustomUserDetails userDetails);
}
