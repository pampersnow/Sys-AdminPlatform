一:spring-boot-starter-security
		应用安全与spring-boot-starter-security
		spring-boot-starter-security 主要面向的是Web应用开发，配合spring-boot-starter-web,所以，对应的maven依赖如下：
		
		       <dependency>
		            <groupId>org.springframework.boot</groupId>
		            <artifactId>spring-boot-starter-web</artifactId>
		        </dependency>
		        <dependency>
		            <groupId>org.springframework.boot</groupId>
		            <artifactId>spring-boot-starter-security</artifactId>
		        </dependency>
		        
		spring-boot-starter-security 默认会提供一个基于HTTP Basic认证的安全防护策略，默认用户为user,访问密码则在当前web应用启动的时候，打印到控制
		台，要想定制，则在配置文件如下进行配置：
		
				security.user.name={username}
				security.user.password={passwd}
		
		除此之外，spring-boot-starter-security还会默认启动一些必要的Web安全防护，比如针对XSS CSRF等场针对Web应用的攻击，同时，也会将一些常见的静态
		资源路径排除在安全防护之外。
		
		AuthenticationManager AccessDecisionManager AbstractSecurityInterceptor被称为Spring Security的基础铁三角，前2者负责制定规则，
		AbstractSecurityInterceptor负责执行。我们常见的Filter类就可以定制和扩展SpringSecurity的防护机制。
		
		进一步定制spring-boot-starter-security
		上诉使用SecurityProperties暴露的配置项，即以security.*对spring-boot-starter-security进行简单的配置，还可以通过给出一个继承了
		WebSecurityConfigurerAdapter的JavaConfig配置类对spring-boot-starter-security的行为进行更深级别的定制，例如：
		
		使用其他的AuthenticationManager实例
		对默认的HttpSecurity定义的资源访问的规则重新定义
		对默认提供的WebSecurity行为进行调整
		一般配合@Order（SecurityProperties.ACCESS_OVERRIDE_ORDER）进行标注。
		
二:commons-lang使用
	这一组API的所有包名都以org.apache.commons.lang开头，共有如下8个包： 
	
	org.apache.commons.lang
	
	org.apache.commons.lang.builder
	
	org.apache.commons.lang.enum
	
	org.apache.commons.lang.enums
	
	org.apache.commons.lang.exception
	
	org.apache.commons.lang.math
	
	org.apache.commons.lang.mutable
	
	org.apache.commons.lang.time
	
	
	commons.lang包共包含了17个实用的类：
						 			
			ArrayUtils – 用于对数组的操作，如添加、查找、删除、子数组、倒序、元素类型转换等；
			
			BitField – 用于操作位元，提供了一些方便而安全的方法；
			
			BooleanUtils – 用于操作和转换boolean或者Boolean及相应的数组；
			
			CharEncoding – 包含了Java环境支持的字符编码，提供是否支持某种编码的判断；
			
			CharRange – 用于设定字符范围并做相应检查；
			
			CharSet – 用于设定一组字符作为范围并做相应检查；
			
			CharSetUtils – 用于操作CharSet；
			
			CharUtils – 用于操作char值和Character对象；
			
			ClassUtils – 用于对Java类的操作，不使用反射；
			
			ObjectUtils – 用于操作Java对象，提供null安全的访问和其他一些功能；
			
			RandomStringUtils – 用于生成随机的字符串；
			
			SerializationUtils – 用于处理对象序列化，提供比一般Java序列化更高级的处理能力；
			
			StringEscapeUtils – 用于正确处理转义字符，产生正确的Java、JavaScript、HTML、XML和SQL代码；
			
			StringUtils – 处理String的核心类，提供了相当多的功能；
			
			SystemUtils – 在java.lang.System基础上提供更方便的访问，如用户路径、Java版本、时区、操作系统等判断；
			
			Validate – 提供验证的操作，有点类似assert断言；
			
			WordUtils – 用于处理单词大小写、换行等。
			

三:swagger用于定义API文档。

	1. swagger2好处：
				
				前后端分离开发
				API文档非常明确
				测试的时候不需要再使用URL输入浏览器的方式来访问Controller
				传统的输入URL的测试方式对于post请求的传参比较麻烦（当然，可以使用postman这样的浏览器插件）
				springfox基于swagger2，兼容老版本
	
	io.springfox 常用注解：
				@Api：用在类上，说明该类的作用
				@ApiOperation：用在方法上，说明方法的作用
				@ApiImplicitParams：用在方法上包含一组参数说明
				@ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
				paramType：参数放在哪个地方
				header-->请求参数的获取：@RequestHeader
				query-->请求参数的获取：@RequestParam
				path（用于restful接口）-->请求参数的获取：@PathVariable
				body（不常用）
				form（不常用）
				name：参数名
				dataType：参数类型
				required：参数是否必须传
				value：参数的意思
				defaultValue：参数的默认值
				@ApiResponses：用于表示一组响应
				@ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
				code：数字，例如400
				message：信息，例如"请求参数没填好"
				response：抛出异常的类
				@ApiModel：描述一个Model的信息（这种一般用在post创建的时候，使用@RequestBody这样的场景，请求参数无法使用@ApiImplicitParam注解进行描述的时候）
				@ApiModelProperty：描述一个model的属性
				
				注意：
				在controller 使用springfox的
				@ApiParam(name="queryCondition", value="查询条件", required=true) @RequestBody QueryCondition queryCondition
				注解，可在页面看到QueryCondition类型参数
				
				
				使用@ApiOperation(value = "Greeting by Name",  
				            notes = "Say hello to the people",  
				            response = SayingRepresentation.class,  
				            position = 0)
				可以看到返回值类型
				
				
四:Quartz是一个任务调度框架。比如你遇到这样的问题
		像每月25号，信用卡自动还款
		像每年4月1日今天是发工资日子
		像每隔1小时，备份一下自己的学习笔记到云盘
	这些问题总结起来就是：在某一个有规律的时间点干某件事。并且时间的触发的条件可以非常复杂（比如每月最后一个工作日的17:50），复杂到需要一
		个专门的框架来干这个事。 Quartz就是来干这样的事，你给它一个触发条件的定义，它负责到了时间点，触发相应的Job起来干活。