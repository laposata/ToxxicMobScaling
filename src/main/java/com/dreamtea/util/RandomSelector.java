package com.dreamtea.util;

import net.minecraft.util.math.random.Random;

import java.util.List;
import java.util.Map;

public class RandomSelector {
  public static <T> T getFromWeighted(Map<T, Integer> options, Random r){
    T t = getFromWeighted(options, 0, r);
    if(t == null){
      throw new IllegalStateException("Somehow picked number outside of range");
    }
    return t;
  }

  public static <T> T getFromWeighted(Map<T, Integer> options, int nothing, Random r){
    if(options.size() == 0){
      return null;
    }
    float f = r.nextFloat();
    int totalWeights = options.values().stream().reduce(Integer::sum).get() + nothing;
    f *= totalWeights;
    for(Map.Entry<T, Integer> entry: options.entrySet()){
      f -= entry.getValue();
      if(f <= 0){
        return entry.getKey();
      }
    }
    return null;
  }

  public static <T> T getFromList(List<T> options, Random r){
    if(options.size() == 0){
      return null;
    }
    int f = (int)(r.nextFloat() * options.size());
    return options.get(f);
  }

  public static int roundRandomly(float i){
    int min = (int)i;
    int bonus = Random.create().nextFloat() < (i - min)? 1 : 0;
    return min + bonus;
  }
}
