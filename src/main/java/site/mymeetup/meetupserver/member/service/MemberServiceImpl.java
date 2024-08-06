package site.mymeetup.meetupserver.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.mymeetup.meetupserver.common.service.S3ImageService;
import site.mymeetup.meetupserver.exception.CustomException;
import site.mymeetup.meetupserver.exception.ErrorCode;
import site.mymeetup.meetupserver.geo.entity.Geo;
import site.mymeetup.meetupserver.geo.repository.GeoRepository;
import site.mymeetup.meetupserver.member.dto.MemberDto;
import site.mymeetup.meetupserver.member.entity.Member;
import site.mymeetup.meetupserver.member.repository.MemberRepository;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final GeoRepository geoRepository;
    private final S3ImageService s3ImageService;

    // 회원 가입
    public MemberDto.MemberSaveRespDto createMember(MemberDto.MemberSaveReqDto memberSaveReqDto) {
        // geoId로 Geo 객체 조회
        Geo geo = geoRepository.findById(memberSaveReqDto.getGeoId())
                .orElseThrow(() -> new CustomException(ErrorCode.GEO_NOT_FOUND));

        Member member = memberRepository.save(memberSaveReqDto.goEntity(geo));
        return MemberDto.MemberSaveRespDto.builder().member(member).build();
    }

    // 특정 회원 조회
    public MemberDto.MemberSelectRespDto getMemberByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        System.out.println(">>geo : " + member.getGeo());

        return MemberDto.MemberSelectRespDto.builder().member(member).build();
    }

    // 회원 수정
    @Override
    public MemberDto.MemberSaveRespDto updateMember(Long memberId, MemberDto.MemberSaveReqDto memberSaveReqDto, MultipartFile image) {
        // 핸드폰 번호로 해당 회원이 존재하는지 검증
        Member member = memberRepository.findByMemberIdAndStatus(memberId, 1)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // geoId로 Geo 객체 조회
        Geo geo = geoRepository.findById(memberSaveReqDto.getGeoId())
                .orElseThrow(() -> new CustomException(ErrorCode.GEO_NOT_FOUND));

        // S3 이미지 업로드
        String originalImg = null;
        String saveImg = null;

        // 이미지 변경하는 경우 업로드 후 DB저장
        if (!image.isEmpty()) {
            saveImg = s3ImageService.upload(image);
            originalImg = image.getOriginalFilename();
        }
        // 이미지를 변경하지 않는 경우 기존 이미지 그대로
        else if (memberSaveReqDto.getOriginalImg() != null && memberSaveReqDto.getSaveImg() != null) {
            if (!memberSaveReqDto.getSaveImg().equals(member.getSaveImg())
                    && !memberSaveReqDto.getOriginalImg().equals(member.getOriginalImg())) {
                throw new CustomException(ErrorCode.IMAGE_BAD_REQUEST);
            }
            originalImg = memberSaveReqDto.getOriginalImg();
            saveImg = memberSaveReqDto.getSaveImg();
        }
        //원본/저장 둘 중 하나만 널일 경우 삭제
        else if (memberSaveReqDto.getOriginalImg() != null || memberSaveReqDto.getSaveImg() != null) {
            throw new CustomException(ErrorCode.IMAGE_BAD_REQUEST);
        }

        // dto -> entity
        member.updateMember(memberSaveReqDto.toEntity(geo, originalImg, saveImg));
        // DB 수정
        Member updatedMember = memberRepository.save(member);

        return MemberDto.MemberSaveRespDto.builder().member(updatedMember).build();
    }
}