package WebCrawler.org.presentation.checker.crawler;

/**
 * @author radio.koza
 * @version 1.0
 * @created 04-5-2014 20:56:23
 */
public class CrawlingState {

	private int pagesCrawled;
	private boolean done;

	public CrawlingState(){
            pagesCrawled = 0;
            done = false;
	}

        public void done(){
            done = true;
        }
        
        public void incCount(){
            pagesCrawled++;
        }
        
        public boolean isDone(){
            return done;
        }
        
        public int getPagesCrawled(){
            return pagesCrawled;
        }
//	public void finalize() throws Throwable {
//
//	}

}