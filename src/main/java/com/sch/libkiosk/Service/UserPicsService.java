package com.sch.libkiosk.Service;

import com.sch.libkiosk.Dto.PicUploadDto;
import com.sch.libkiosk.Dto.SignupDto;
import com.sch.libkiosk.Dto.UserPicsDto;
import com.sch.libkiosk.Entity.User;
import com.sch.libkiosk.Entity.UserPics;
import com.sch.libkiosk.Repository.UserPicsRepository;
import com.sch.libkiosk.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserPicsService {
    private final UserRepository userRepository;
    private final UserPicsRepository userPicsRepository;

    @Transactional
    public Boolean isExist(Long uid){
        try {
            userPicsRepository.findByUserId(uid);
        } catch (EntityNotFoundException E){
            log.error("User NotFound");
            return false;
        }
        return true;
    }
    @Transactional
    public Boolean uploadUserPic(Long uid, List<MultipartFile> pics) throws FileNotFoundException, IOException {
        //TODO:사진을 서버 특정 디렉토리에 저장 후, 해당 디렉토리 경로를 입력 후 저장 구현.

        //파일이 비었는지 확인
        if(pics.isEmpty()){
            log.error("Wrong pics");
            throw new IllegalStateException("File is Empty");
        }else { //파일이 비지 않았을 경우

            String f_path = "../../../resources/UserPics/" + uid.toString();

            //UserPics 아래에 uid 경로 존재여부 확인
            URL url = getClass().getResource(f_path);
            Boolean isExist = null;
            Boolean isDir = null;

            //경로가 아닐경우 예외
            try{
//                isDir = new File(url.getFile()).isDirectory();
//                파일이 존재하는지 확인
                isExist = new File(url.getFile()).exists();
                if(!isExist){
                    throw new FileNotFoundException();

                    //존재하지 않는경우 생성
                    File directory = new File(f_path);
                    //파일 생성 실패시
                    if(!directory.exists()) {
                        throw new IOException("Failed to make dir: " + f_path);
                    }
                    //파일 생성 성공시 계속 진행
                }

                //존재하는 경우
                for (MultipartFile pic : pics){
                    //파일 확장자 체크
                    String contentType = pic.getContentType();
                    String originalFileExtension;

                    //확장자 명이 없는경우 다음 파일로 넘어감
                    if(ObjectUtils.isEmpty(contentType)){
                        break;
                        //확장자 존재할 경우
                        //jpg, png 이외의 확장자는 넘어감
                    }else{
                        if(contentType.contains("image/jpeg")){
                            originalFileExtension = ".jpg";
                        } else if(contentType.contains("image/png")){
                            originalFileExtension = ".png";
                        } else {
                            break;
                        }
                    }

                    //파일명 중복 피하기 위해 나노초를 파일명으로 사용
                    long nanoTime = System.nanoTime();
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
                    String picName = format.format(new Date(nanoTime / 1000000));

                    String finalPath = "..\\..\\..\\resources\\UserPics\\" + picName + originalFileExtension;
                    //파일 명 지정 후 경로에 저장
                    try{
                        pic.transferTo(new File(finalPath));

                    }catch(IOException e){
                        throw new IOException("Failed to save local storage");
                    }

                    Optional<User> user = userRepository.findById(uid);
                    //저장 후 경로 및 uid DB에 저장
                    //user 존재 여부는 위 isExist에서 확인했기때문에 필요 없음.
                    UserPicsDto userpicsDto = UserPicsDto.builder()
                            .user(user.get())
                            .picUrl(finalPath)
                            .build();

                    //결과 반환
                    userPicsRepository.save(userpicsDto.toEntity());
                    return true;
                }

            }catch (FileNotFoundException e) {
                log.error("Failed to check if directory exists: " + url.getFile());
            }
        }
        return false;
    }

    @Transactional
    public UserPicsDto getUserPics(Long userId){
        UserPics up = userPicsRepository.findByUserId(userId);
        UserPicsDto userPicsDto = UserPicsDto.builder()
                .user(up.getUser())
                .picUrl(up.getPicPath())
                .build();

        return userPicsDto;
    }
}
