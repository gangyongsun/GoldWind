package cn.com.goldwind.kis.mmseg.main.rule;

import cn.com.goldwind.kis.mmseg.main.Chunk;

/**
 * 源码来源： http://blog.chenlb.com/category/mmseg4j
 <p>
 * Largest Average Word Length.<p/>
 * 
 * 长度(Length)/词数
 * 
 * @see http://technology.chtsai.org/mmseg/
 * 
 * @author suny 20140818
 */
public class LargestAvgLenRule extends Rule {

	private double largestAvgLen;
	
	@Override
	public void addChunk(Chunk chunk) {
		if(chunk.getAvgLen() >= largestAvgLen) {
			largestAvgLen = chunk.getAvgLen();
			super.addChunk(chunk);
		}
	}

	@Override
	protected boolean isRemove(Chunk chunk) {
		return chunk.getAvgLen() < largestAvgLen;
	}

	@Override
	public void reset() {
		largestAvgLen = 0;
		super.reset();
	}

}
