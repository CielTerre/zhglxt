package com.seezoon.framework.common.context.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.seezoon.framework.common.context.utils.NDCUtils;

@Component
public class SystemStartEventHandler implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		NDCUtils.push();
		if (event.getApplicationContext().getParent() == null) {
			printKeyLoadMessage();
		}
	}
	/**
	 * 获取Key加载信息
	 */
	public static void printKeyLoadMessage(){
	}
}

