package site.mymeetup.meetupserver.board.control;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.mymeetup.meetupserver.board.dto.BoardDto;
import site.mymeetup.meetupserver.board.service.BoardService;
import site.mymeetup.meetupserver.response.ApiResponse;

@RestController
@RequestMapping("/api/v1/crews/{crewId}/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 게시글 등록
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<?> createBoard(@PathVariable Long crewId,
                                      @RequestBody @Valid BoardDto.BoardSaveReqDto boardSaveReqDto) {
        return ApiResponse.success(boardService.createBoard(crewId, boardSaveReqDto));
    }

    // 게시글 이미지 ajax 처리
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/images")
    public ApiResponse<?> uploadImage(@RequestPart @Valid MultipartFile[] images) {
        return ApiResponse.success(boardService.uploadImage(images));
    }

}
