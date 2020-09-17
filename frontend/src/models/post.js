export default class Post {
	constructor(pid, title, author, content, publishedTime, thumbnail) {
		this.pid = pid;
		this.title = title;
		this.author = author;
		this.content = content;
		this.publishedTime = publishedTime;
		this.thumbnail = thumbnail;
	}
}