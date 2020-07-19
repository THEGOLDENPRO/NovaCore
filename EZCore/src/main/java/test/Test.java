package test;

import xyz.zeeraa.ezcore.module.EZModule;

public class Test {

	public static void main(String[] args) {
		EZModule module = new TestModule();

		System.out.println(module.getClassName());
	}
}