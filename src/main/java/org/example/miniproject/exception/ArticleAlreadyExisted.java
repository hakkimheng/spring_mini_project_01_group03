package org.example.miniproject.exception;

public class ArticleAlreadyExisted extends RuntimeException {
    public ArticleAlreadyExisted(String message) {
        super(message);
    }
}
