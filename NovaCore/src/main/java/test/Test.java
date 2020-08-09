package test;

import xyz.zeeraa.novacore.module.NovaModule;
import xyz.zeeraa.novacore.utils.RandomGenerator;
import xyz.zeeraa.novacore.world.worldgenerator.worldpregenerator.WorldPreGenerator;

public class Test {

	public static void main(String[] args) {
		NovaModule module = new TestModule();

		System.out.println(module.getClassName());
		
		for(int i = 0; i < 24; i++) {
			int number = i+1;
			
			System.out.println(((number - 1) % 12) + 1);
		}
		
		for(int i = 0; i < 10; i++) {
			System.out.println(RandomGenerator.generate(1, 3));
		}
	}
}