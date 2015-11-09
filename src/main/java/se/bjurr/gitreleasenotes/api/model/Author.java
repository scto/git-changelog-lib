package se.bjurr.gitreleasenotes.api.model;

import java.util.List;

public class Author {
 private final List<Commit> commits;
 private final String authorName;
 private final String authorEmail;

 public Author(String authorName, String authorEmail, List<Commit> commits) {
  this.authorName = authorName;
  this.authorEmail = authorEmail;
  this.commits = commits;
 }

 public String getAuthorEmail() {
  return authorEmail;
 }

 public String getAuthorName() {
  return authorName;
 }

 public List<Commit> getCommits() {
  return commits;
 }
}
