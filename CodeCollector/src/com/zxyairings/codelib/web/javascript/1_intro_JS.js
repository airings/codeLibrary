/**
 * 
 * JavaScript 概述
 * 
 * JavaScript是基于对象和事件驱动的脚本语言，主要应用于客户端。(而JSP是应用于服务端)
 * 特点：
 * 		1. 交互性 - 他可以做的就是信息的动态交互
 * 		2. 安全性 - 不允许直接访问本地硬盘
 * 		3. 跨平台性 - 只要是可以解释JS的浏览器都可以运行JS，和平台无关。
 * 
 * JavaScript和Java不同
 * 	1. JS是基于对象，它里面的内容被浏览器加载进来后都是对象，可以直接拿过来使用。Java是面向对象
 *  2. JS只需解释就可以执行，Java需要先编译成字节码文件，再执行
 *  3. JS是弱类型，Java是强类型
 *  
 * JavaScript的组成:
 * 	1. ECMAScript: 由ECMA组织制定的js语法，语句等。
 * 			-ECMA: 欧洲计算机协会
 * 	2. BOM: Browser Object Model 浏览器对象模型 
 * 			-window
 *  3. DOM: Document Object Model 文档对象模型 
 *  		-document
 *  
 *  JavaScript与HTML的结合方式
 *  	想要将其他代码融入到HTML中，都是以标签的形式
 *  	1. JS代码存放在标签对<script> js code ... </script>中
 *  	2. 当有多个html页面使用到相同的JS脚本时，可以讲js代码封装到一个文件中，只要在script标签的src属性引入一个js文件。(方便后期维护，扩展)
 *  		注意：如果在script标签中定义了src属性，那么标签中的内容不会被执行
 *  		例：<script src="test.js" type="text/javascript"></script>
 *  	注：规范中script标签早起有一个属性language，而现在使用type属性
 *  
 *  JavaScript语法
 *  	JS的原始类型：都使用使用关键字var来声明
 *  		1. string 字符串
 *  		2. number 数字
 *  		3. boolean true/false
 *  		4. null 表示对象引用为空 var date = new Date(); 
 *  			JS中所有对象的引用默认也是Object
 *  		5. undefined 定义一个变量，但是变量没有赋值
 *  		6. object
 *  
 *  	typeof(变量名称): 可以查看变量类型
 *  
 * 	JavaScript数组
 * 
 * 	JavaScript函数
 * 		1. 由于JS函数中有一个argument数组用以存储传入的参数，所以调用JS函数，想传入几个参数就可以传入几个参数，所以它不存在方法的重载，只要使用函数的名称就是对这个函数的调用。
 * 		2. 函数也是一个对象，所以也可以有多个引用(函数名)同时指向同一个函数对象。函数的引用可以赋给新的变量 var sum = function(){ ... }
 * 		3. 匿名函数
 * 		4. 动态函数
 * 		5. 全局函数：由于这类不属于任何一个对象，JS将他们统一管理。直接写名称使用
 * 	
 * 
 * JavaScript全局变量和局部变量
 * 		1.全局变量：在script标签里面定义一个变量，这个变量在页面中JS部分都可以使用
 * 			- 在方法内部可见，在方法外部可见，在另一个script标签中可见
 * 		2.局部变量：在方法内部定义一个变量，只能在方法内部使用
 * 	
 * 
 * script标签放在的位置
 * 		1. script标签可以放在html文件的任何位置，甚至可以放在<html>标签外
 * 		2. 因为html文件是从上向下逐行解析的，所以如果在script标签中的JS代码引用或操作的其他标签，那么script标签就必须放在引用或操作的其他标签的后面。
 * 		3. 一般建议将script标签放在</body>标签的后面，这样等前面的标签都解析完了，script中的JS代码就可以毫无顾虑的运行了。
 * 
 * JavaScript原型
 * 
 * JavaScript对象
 * 		1. 定义一个函数，作为构造器
 * 		2. 定义一个函数，作为类的定义区域
 * 		3. 通过类定义字面量来定义类，字面量即dictionary
 * 		3. 对象调用成员有两种方式：对象.属性名    对象["属性名"]
 */