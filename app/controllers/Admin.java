package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class Admin extends Controller {

    @Before
    static void setConnectedUser() {
        if (Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user.fullname);
        }
    }

    public static void index() {
        List<Post> posts = Post.find("author.email", Security.connected()).fetch();
        render(posts);
    }

    public static void form(Long id) {
        if (id == null) {
            render();
        } else {
            Post post = Post.findById(id);
            render(post);
        }
    }

    public static void save(Long id, @Required(message = "Title is required") String title,
            @Required(message = "Content is required") String content, String tags) {
        Post post;
        if (id == null) {
            User author = User.find("byEmail", Security.connected()).first();
            post = new Post(author, title, content);
        } else {
            post = Post.findById(id);
            post.title = title;
            post.content = content;
            post.tags.clear();
        }

        for (String tagName : tags.split("\\s")) {
            if (tagName.trim().length() > 0) {
                post.tagItWith(tagName);
            }
        }

        if (validation.hasErrors()) {
            render("@form", post);
        }

        post.save();
        index();
    }
    
    public static void delete(Long id){
        Post post = Post.findById(id);
        post.delete();
        
        index();
    }
}