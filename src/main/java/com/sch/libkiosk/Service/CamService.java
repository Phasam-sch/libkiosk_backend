package com.sch.libkiosk.Service;

import com.sch.libkiosk.Dto.LoginDto;
import com.sch.libkiosk.Entity.User;
import com.sch.libkiosk.Repository.UserPicsRepository;
import com.sch.libkiosk.Repository.UserRepository;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
//@ServerEndpoint(value="sock")
public class CamService {

    private final UserPicsRepository userPicsRepository;
    private final UserRepository userRepository;

    @Value("${userPics.dir}")
    private String userPicsDir;

    @Transactional
    public LoginDto uploadLoginPic(List<MultipartFile> loginPics) throws IOException{

        if(loginPics.isEmpty()){
            //파일이 비어있는 경우
            log.error("EmptyFile");
            throw new IllegalStateException("File is Empty");
        }else {
            //파일이 비어있지 않을 경우
            //로그인 사진 저장 경로
            String f_path = userPicsDir + "/temp";

//            Boolean isExist = null;

            try{
//                isExist = new File(f_path).exists();
                if(!new File(f_path).exists()){
                    //경로가 존재하지 않을 경우 경로 생성
                    File directory = new File(f_path);

                    if(!directory.mkdirs()){
                        throw new IOException("Fail to make dir: " + f_path);
                    }

                }

                //경로 확인 후 사진 저장
                for(MultipartFile pic : loginPics){
                    String contentType = pic.getContentType();
                    String originalFileExtension;

                    if(ObjectUtils.isEmpty(contentType)){
                        break;
                    }else{
                        if(contentType.contains("image/jpeg")){
                            originalFileExtension = ".jpg";
                        }else{
                            break;
                        }
                    }

                    String picName = "loginPic";

                    String finalPath = f_path + "/" + picName + originalFileExtension;

                    try {
                        pic.transferTo(new File(finalPath));
                    }catch(IOException e){
                        throw new IOException("Fail to save local storage");
                    }
                }

                //TODO: docker 내 UBUNTU 서버 실행 구현
                log.info("ubuntu 실행");
                Long LoginUser = -1L;

                LoginUser = 1L;

                //사용자 판별할 수 없을 경우 로그인 종료

                if(LoginUser != -1L) {
                    Optional<User> optionalUser = userRepository.findById(LoginUser);
                    if (optionalUser.isPresent()) {
                        User retUser = optionalUser.get();
                        log.info("[안면인식] 사용자 ID: " + retUser.getLoginId() + "  사용자 PW : " + retUser.getPassword());

                    return LoginDto.builder()
                                .loginId(retUser.getLoginId())
                                .password(retUser.getPassword())
                                .build();
                    } else {
                        throw new IllegalStateException("로그인 실패 1");
                    }
                }

            }catch(FileNotFoundException e){
                log.error("dir not found" + f_path);
            }
        }
        throw new IllegalStateException("로그인 실패 2");
    }

    public void sendMessage() throws Exception  {
        String host = "192.0.0.1";
        int port = 8080;

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 여기에 소켓 통신 설정을 추가합니다.
                            ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8)); // String encoder
                            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8)); // 문자열 디코더
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();

            // 메시지 전송 로직을 추가
            future.channel().writeAndFlush("test");

            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

//    private static Set<Session> clients =
//            Collections.synchronizedSet(new HashSet<Session>());
//
//    @OnMessage
//    public void sendMsg(String func, Session session) throws Exception {
//        log.info("[세션 전송] : " + func);
//        if(func.equals("signup") || func.equals("login") || func.equals("rfidscan")){
//            for (Session s : clients) {
//                System.out.println("send data : " + func);
//                s.getBasicRemote().sendText(func);
//            }
//        }
//    }
//    @OnOpen
//    public void onOpen(Session s){
//        log.info("[try]Open Session: " + s.toString());
//
//        if(!clients.contains(s)){
//            clients.add(s);
//            log.info("[Success]Session Open : " + s );
//        }else {
//            log.info("[fail] Session already connected");
//            }
//    }
//
//    @OnClose
//    public void onClose(Session s){
//        log.info("Closed Session : " + s);
//        clients.remove(s);
//    }

}
