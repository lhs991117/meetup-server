package site.mymeetup.meetupserver.album.service;

import org.springframework.web.multipart.MultipartFile;
import static site.mymeetup.meetupserver.album.dto.AlbumLikeRespDto.AlbumLikeSaveRespDto;

import static site.mymeetup.meetupserver.album.dto.AlbumDto.AlbumRespDto;
import static site.mymeetup.meetupserver.album.dto.AlbumDto.AlbumSaveRespDto;
import java.util.List;

public interface AlbumService {

    List<AlbumSaveRespDto> createAlbum(Long crewId, List<MultipartFile> images);

    List<AlbumRespDto> getAlbumByCrewId(Long crewId);

    AlbumRespDto getAlbumByCrewIdAndAlbumId(Long crewId, Long albumId);

    void deleteAlbum(Long crewId, Long albumId);

    boolean isLikeAlbum(Long crewId, Long albumId);

    AlbumLikeSaveRespDto likeAlbum(Long crewId, Long albumId);
}
