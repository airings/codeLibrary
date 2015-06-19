package com.zxyairings.codelib.spring.simulation;

import org.apache.commons.beanutils.ConvertUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模拟Spring容器如何从xml配置文件创建bean
 * 传智传客版容器
 *
 */

public class SimulateClassPathXMLApplicationContext {
//	可以配置多个bean
	private List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
	private Map<String, Object> singletonsMap = new HashMap<String, Object>();
	
	public SimulateClassPathXMLApplicationContext(String filename) {
		this.readXML(filename);
		this.instanceBeans();
		this.insertObject();//注入对象
	}
	
//	为bean对象的属性注入值
	private void insertObject() {
		for(BeanDefinition beanDefinition : beanDefinitions){
			Object bean = singletonsMap.get(beanDefinition.getId());
			if(bean!=null){
				try {
					PropertyDescriptor[] ps = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
					for(PropertyDefinition propertyDefinition : beanDefinition.getProperties()){
						for (PropertyDescriptor propertyDescriptor : ps) {
							if(propertyDefinition.getName().equals(propertyDescriptor.getName())){
								Method setter = propertyDescriptor.getWriteMethod();//获取属性的setter方法
								if (setter!=null) {
									Object value = null;
									if (propertyDefinition.getRef() != null && !"".equals(propertyDefinition.getRef().trim())) {
										value = singletonsMap
												.get(propertyDefinition
														.getRef()); //把引用对象注入到属性中

									} else{
										value = ConvertUtils.convert(propertyDefinition.getValue(), propertyDescriptor.getPropertyType());//注入基本类型
									}
									setter.setAccessible(true);//解决setter是private的情况
									setter.invoke(bean, value);//注入引用对象或者基本类型
								}
							}
						}
					}
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}
		
	}
	/**
	 * 完成bean的实例化
	 */
	private void instanceBeans() {
		for (BeanDefinition beanDefinition : beanDefinitions) {
			try {
				if(beanDefinition.getClassName() != null && !"".equals(beanDefinition.getClassName().trim()))
					singletonsMap.put(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).newInstance());
			} catch (Exception e) {	
				e.printStackTrace();
			} 
		}
	}
	/**
	 * 读取xml配置文件
	 * @param filename
	 */
	private void readXML(String filename) {
	       SAXReader saxReader = new SAXReader();   
	        Document document=null;   
	        try{
	         URL xmlpath = this.getClass().getClassLoader().getResource(filename);
	         document = saxReader.read(xmlpath);
	         Map<String,String> nsMap = new HashMap<String,String>();
	         nsMap.put("ns","http://www.springframework.org/schema/beans");//加入命名空间
	         XPath xsub = document.createXPath("//ns:beans/ns:bean");//创建beans/bean查询路径
	         xsub.setNamespaceURIs(nsMap);//设置命名空间
	         List<Element> beans = xsub.selectNodes(document);//获取文档下所有bean节点 
	         for(Element element: beans){
	            String id = element.attributeValue("id");//获取id属性值
	            String clazz = element.attributeValue("class"); //获取class属性值        
	            BeanDefinition beanDefine = new BeanDefinition(id, clazz);
//	            获取property属性
	            XPath propertysub = element.createXPath("ns:property");
	            propertysub.setNamespaceURIs(nsMap);//设置命名空间
	            List<Element> properties = propertysub.selectNodes(element);
	            for(Element property: properties){
	            	String propertyName = property.attributeValue("name");
	            	String propertyRef  = property.attributeValue("ref");
	            	String propertyValue = property.attributeValue("value");
	            	PropertyDefinition propertyDefinition = new PropertyDefinition(propertyName, propertyRef,propertyValue);
	            	beanDefine.getProperties().add(propertyDefinition);
	            }
	            beanDefinitions.add(beanDefine);
	         }   
	        }catch(Exception e){   
	            e.printStackTrace();
	        }
	}
	/**
	 * 获取bean实例
	 * @param beanName
	 * @return
	 */
	
	public Object getBean(String beanName) {
		return singletonsMap.get(beanName);
	}
}
