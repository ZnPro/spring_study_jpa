package com.cos.myjpa.web;

import java.nio.channels.IllegalChannelGroupException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.myjpa.domain.post.Post;
import com.cos.myjpa.domain.post.PostRepository;
import com.cos.myjpa.web.dto.CommonRespDto;
import com.cos.myjpa.web.post.dto.PostSaveReqDto;
import com.cos.myjpa.web.post.dto.PostUpdateReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TestController {
	
	private final PostRepository postRepository;

	@PostMapping("/test/post")
	public CommonRespDto<?> save(@RequestBody PostSaveReqDto postSaveReqDto) {//title, content
		Post postEntity= postRepository.save(postSaveReqDto.toEntity());//실패하면 무조건 익셉션됨
		//title,content를 toEntitiy를 통해서 post타입으로 바꿔준다. 그리고 그 post만 save
		return new CommonRespDto<>(1, "성공", postEntity);
	}
	
	@GetMapping("/test/post")
	public CommonRespDto<?> findAll(){
		List<Post> postsEntity= postRepository.findAll();
		return new CommonRespDto<>(1, "성공", postsEntity);
	}
	
	@GetMapping("/test/post/{id}")
	public CommonRespDto<?> findById(@PathVariable Long id){		
		//인터페이스 안에 함수가 하나 있으면 화살표 함수 사용 가능
		Post postEntity= postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없음");
		});
		return new CommonRespDto<>(1, "성공", postEntity);
	}
	
	@PutMapping("/test/post/{id}")
	public CommonRespDto<?> update(@PathVariable Long id, @RequestBody PostUpdateReqDto postUpdateReqDto){
		Post postEntity= postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없음");
		});
		
		postEntity.setTitle(postUpdateReqDto.getTitle());
		postEntity.setContent(postUpdateReqDto.getContent());
		
		Post postUpdateEntity= postRepository.save(postEntity);//더티 체킹을 사용해야 하는데 그러려면 @Service만들어야 가능함
		//Post postUpdateEntity= postRepository.save(PostUpdateReqDto.toEntity);//이러면안됨
		return new CommonRespDto<>(1, "성공", postUpdateEntity);
	}
	
	@DeleteMapping("/test/post/{id}")
	public CommonRespDto<?> deleteById(@PathVariable Long id){
		postRepository.deleteById(id);
		return new CommonRespDto<>(1, "성공", null);
	}
	
}