package com.zxyairings.codelib.javabean;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/*
 * 内省对应的英文单词为IntroSpector，它主要用于对JavaBean进行操作，
 * JavaBean是一种特殊的Java类，其中的某些方法符合某种命名规则，就是set和get方法
 * 如果一个Java类中的一些方法符合某种命名规则，则可以把它当作JavaBean来使用。
 * 
 *  * IntroSpector -> JavaBean -> 特殊的java类
 * 
 * Class Person{
 * 	private int x;
 * 	public int getAge(){
 * 		return x;
 *  }
 * 	public void setAge(int age){
 * 		this.x = age
 * 	}
 * }
 * 
 * JavaBean是一种特殊的Java类，主要用于传递数据信息，这种java类中的方法主要用于访问私有的字段，且方法名符合某种命名规则。
 * 如果要在两个模块之间传递多个信息，可以将这些信息封装到一个JavaBean中，这种JavaBean的实例对象通常称之为值对象（Value Object，简称VO）。
 * 这些信息在类中用私有字段来存储，如果读取或设置这些字段的值，则需要通过一些相应的方法来访问，
 * JavaBean的属性是根据其中的setter和getter方法来确定的，而不是根据其中的成员变量。
 * 
 * 去掉set前缀，剩余部分就是属性名，如果剩余部分的第二个字母是小写的，则把剩余部分的首字母改成小的。如果第二个字母是大写，则其余部分不变。
 * setId()的属性名 ->id
isLast()的属性名 -> last
setCPU的属性名是什么? -> CPU
getUPS的属性名是什么？-> UPS

总之，一个类被当作javaBean使用时，JavaBean的属性是根据方法名推断出来的，它根本看不到java类内部的成员变量。

 * 
 * 一个符合JavaBean特点的类可以当作普通类一样进行使用，但把它当JavaBean用肯定需要带来一些额外的好处，我们才会去了解和应用JavaBean！
 * 
 * 好处如下：
1. 在Java EE开发中，经常要使用到JavaBean。很多环境就要求按JavaBean方式进行操作，别人都这么用和要求这么做，那你就没什么挑选的余地！
2. JDK中提供了对JavaBean进行操作的一些API，这套API就称为内省。如果要你自己去通过getX方法来访问私有的x，怎么做，有一定难度吧？用内省这套api操作JavaBean比用普通类的方式更方便。
 *
 *
 * IntroSpectorSimpleDemo 是使用内省的简单方法
 * 
 * IntroSpectorComplexDemo 是使用内省的复杂方法
 * 		采用遍历BeanInfo的所有属性方式来查找和设置某个RefectPoint对象的x属性。
 * 		在程序中把一个类当作JavaBean来看，就是调用IntroSpector.getBeanInfo方法， 
 * 		得到的BeanInfo对象封装了把这个类当作JavaBean看的结果信息。
 * 
 * 如果是想得到javabean的某一个属性的值就使用简单方法IntroSpectorSimpleDemo
 * 如果是想得到一个javabean所有属性的值就是用复杂方法IntroSpectorComplexDemo，因为IntroSpector.getBeanInfo返回一个属性数组，我们需要遍历它找到我们要德属性。
 * 
 * 在实际工作中，BeanUtils比内省更常用，要熟练掌握。内省只要见识过，知道就可以了。
 *
 */

public class IntroSpectorSimpleDemo {
	
	public static void main(String[] args) throws Exception {
		
		ReflectPoint pt1 = new ReflectPoint(3,5);
		
		String propertyName = "x";
		//我们想获得属性x的value，那么就要通过如下方式来先根据JavaBean的方法命名规则得到x的getx方法，
		//然后在用反射来调用该方法，这样非常麻烦
		//"x"-->"X"-->"getX"-->MethodGetX-->
		//java 提供了 内省 API 来简化上面的步骤！这就是内省的意义。
		Object retVal = getProperty(pt1, propertyName);
		System.out.println(retVal);
		
		Object value = 7;
		
		setProperties(pt1, propertyName, value);
		System.out.println(pt1.getX());
	}
	
	//内省方式得到x的set方法
	private static void setProperties(Object pt1, String propertyName,
			Object value) throws IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		PropertyDescriptor pd2 = new PropertyDescriptor(propertyName,pt1.getClass());
		Method methodSetX = pd2.getWriteMethod();//得到该属性的set方法
		methodSetX.invoke(pt1,value);//调用该set方法
	}

	//内省方式得到x的get方法
	private static Object getProperty(Object pt1, String propertyName)
			throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		//pt1当做javabean来看
		PropertyDescriptor pd = new PropertyDescriptor(propertyName,pt1.getClass());
		Method methodGetX = pd.getReadMethod();//这样就得到了属性对应的get方法
		Object retVal = methodGetX.invoke(pt1);//调用该get方法
		return retVal;
	}

}
