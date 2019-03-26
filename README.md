# Multi-threaded-Crawler
	* A scalable multi-threaded system for crawling PTT articles.  
	* Provide users with an interface to crawl articles based on user-defined standard.

## Output format
	{
		author: Author,
		title: Title,
		board: Board,
		content: Content,
		date: Date,
		ip: IP,
		url: URL,
		pushList:	[{
					push_userid: UserID,
					push_content: Content,
					push_ipdatetime: Datetime,
					push_tag: Tag
				},
				{
					...
				},
				{
					...
				}]
	}

## Manual
	Once the first initialization is completed, the system will automatically update all documents in the future. 	
	1. Add (subscribed board) into "pttBoard" for initialization.
	2. Check user-defined standard.	
	
	For example: Add "Lifeismoney"  	
	1. pttBoard: Lifeismoney

## Concept

	In a period of time, the cumulative number of pushes does not necessarily reach the popularity standard.
	Therefore, we need to review the articles within 10 pages.

## System Flow

![](https://github.com/r03921081/Multi-threaded-Crawler/blob/master/pttCrawler/Image/Crawler.png)

## Threads life
	1. Dispatcher
		* New: When getting a board to be processed.
		* Terminated: When the system finishes assigning all the article URLs that need to be crawled.
	2. Crawler
		* New: At the beginning of the program.
		* Terminated: At the end of the program.

## Documents
	* UserDefinedFunction.java
		* User-defined functions for articles that meet the standards.
	* pttBoard  
		* Record the PTT board that the user wants to crawl.  
		- (Board)  
	* doneList  
		* Record article information that has been crawled.  
		- (Board, ArticleURL(ID), ArticleDate)  
