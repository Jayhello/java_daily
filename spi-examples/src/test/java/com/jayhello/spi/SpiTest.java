package com.jayhello.spi;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpiTest {

    @Test
    void testCustomLoaderFindsExactlyTwoImplementations() {
        List<Animal> animals = CustomServiceLoader.load(Animal.class).loadAll();
        assertEquals(2, animals.size());
    }

    @Test
    void testAllImplementationsReturnNonNullName() {
        List<Animal> animals = CustomServiceLoader.load(Animal.class).loadAll();
        for (Animal animal : animals) {
            assertNotNull(animal.getName());
            assertFalse(animal.getName().isEmpty());
        }
    }

    @Test
    void testAllImplementationsReturnNonNullSound() {
        List<Animal> animals = CustomServiceLoader.load(Animal.class).loadAll();
        for (Animal animal : animals) {
            assertNotNull(animal.sound());
            assertFalse(animal.sound().isEmpty());
        }
    }

    @Test
    void testDogImplementation() {
        Animal dog = findAnimalByName("Dog");
        assertNotNull(dog);
        assertEquals("Woof", dog.sound());
    }

    @Test
    void testCatImplementation() {
        Animal cat = findAnimalByName("Cat");
        assertNotNull(cat);
        assertEquals("Meow", cat.sound());
    }

    private Animal findAnimalByName(String name) {
        List<Animal> animals = CustomServiceLoader.load(Animal.class).loadAll();
        for (Animal animal : animals) {
            if (name.equals(animal.getName())) {
                return animal;
            }
        }
        return null;
    }
}
