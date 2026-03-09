package com.jayhello.spi;

import java.util.ServiceLoader;

/**
 * SPI 加载示例：使用 java.util.ServiceLoader 加载 Animal 接口的所有实现
 *
 * <p>Java SPI 机制要点：
 * <ol>
 *   <li>定义服务接口（本例为 {@link Animal}）</li>
 *   <li>提供一个或多个实现类（{@code DogImpl}、{@code CatImpl}）</li>
 *   <li>在 {@code META-INF/services/<接口全限定名>} 文件中列出实现类的全限定名</li>
 *   <li>通过 {@link ServiceLoader#load(Class)} 在运行时发现并加载所有实现</li>
 * </ol>
 */
public class SpiDemo {

    public static void main(String[] args) {
        ServiceLoader<Animal> loader = ServiceLoader.load(Animal.class);
        System.out.println("Loaded Animal implementations via SPI:");
        for (Animal animal : loader) {
            System.out.println("  " + animal.getName() + " says: " + animal.sound());
        }
    }
}
