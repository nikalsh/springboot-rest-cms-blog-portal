package se.nackademin.restcms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import se.nackademin.restcms.crudrepositories.BlogPostRepository;
import se.nackademin.restcms.crudrepositories.BlogRepository;
import se.nackademin.restcms.entities.Blog;
import se.nackademin.restcms.entities.BlogPost;
import se.nackademin.restcms.exception.MyFileNotFoundException;
import se.nackademin.restcms.security.UserDetailsImpl;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class BlogPostServiceImpl implements BlogPostService {

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private BlogPostRepository blogPostRepository;


    public BlogPost storePost(String file, String postId) {
        //get the current authenticated user
       // UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //check the current users blog id
       // user.getUser().getBlog().getBlogAdmin().getId();

        //decide if they can create a blogpost
        //...
        LocalDateTime localDateTime = LocalDateTime.now();
        Optional<BlogPost> blogPostOptional = blogPostRepository.findById(postId);
        BlogPost blogPost;
        if (blogPostOptional.isPresent()) {
            blogPost = blogPostOptional.get();
            blogPost.setPostData(file);
            blogPost.setLastUpdated(localDateTime);
        } else {
            blogPost = new BlogPost(
                   // blogRepository.getOne(user.getUser().getBlog().getBlogAdmin().getId()),
                    blogRepository.getOne(4L),
                    localDateTime,
                    localDateTime,
                    file
            );
        }

        return blogPostRepository.save(blogPost);

    }

    @Override
    public BlogPost getPost(String fileId) {

        return blogPostRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

}
