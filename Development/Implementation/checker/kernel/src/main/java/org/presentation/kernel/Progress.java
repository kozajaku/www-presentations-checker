package org.presentation.kernel;

/**
 *
 * @author radio.koza
 */
public class Progress {
    
    private final int pageLimit;
    private final int pagesCrawled;
    
    public Progress(int pageLimit, int pagesCrawled){
        this.pageLimit = pageLimit;
        this.pagesCrawled = pagesCrawled;
    }
    
    public double percentDone(){
        if (pagesCrawled >= pageLimit){
            return 100.0;
        }
        return (((double)pagesCrawled) / pageLimit) * 100.0;
    }

    public int getPageLimit() {
        return pageLimit;
    }

    public int getPagesCrawled() {
        return pagesCrawled;
    }
    
}
