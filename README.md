# Multi-threaded-Crawler
- An extensible multi-threaded system for crawling multiple website articles.  
	* Provide users with an interface to crawl articles based on user-defined standard.
	* By default, the system saves the crawl results to txt files.

## Extensiblity
		- Add a new website source.
		1. Dispatcher
		2. Crawler
		3. Article format

## Output format
- Real system outputs  
https://github.com/r03921081/Multi-threaded-Crawler/tree/master/multiCrawler/Data  

1. PTT  
https://www.ptt.cc/bbs/index.html 

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
	
2. AppleDaily    
https://tw.appledaily.com/new/realtime/

		{
			title: Title,
			view: View,
			date: Date,
			content: Content,
			url: URL,
			keywords: [ keyword_1, keyword_2, ... ]
		}
	
## Manual
	Once the first initialization is completed, the system will automatically update all documents in the future. 	
	1. Add (subscribed web-subscribed board) into "websites" for initialization.
	2. Check user-defined standard.	
	
	For example: 
	1. Add PTT "Lifeismoney"  	
	  - websites: PTT-Lifeismoney
	2. Add AppleDaily "life"
	  - websites: APPLE-life

## System Flow

![](https://github.com/r03921081/Multi-threaded-Crawler/blob/master/multiCrawler/Image/Crawler.png)

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
	* websites  
		* Record the source of the website that the user wants to crawl. 
		- (Websites-Board)  
		- (PTT-Gossiping)
	* doneList  
		* Record article information that has been crawled.  
		- (Board ArticleURL(ID) ArticleDate)  
		- (PTT-Gossiping /bbs/Gossiping/M.1553917773.A.69A.html 3/30)

## PTT Concept

	In a period of time, the cumulative number of pushes does not necessarily reach the popularity standard.
	Therefore, we need to review the articles within 10 pages.

## APPLEDAILY Mapping keywords
	生活	life
	財經	busi
	地產	property
	政治	polit
	體育	sport
	副刊	strange
	社會	local
	3C	ccc
	娛樂	enter
	論壇	blog
	國際	inter
	時尚	fashion
	壹週刊	nextmag
	車市	auto
