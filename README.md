官方依赖
https://github.com/google/dagger

参考github
 https://github.com/tongchexinfeitao/Dragger2Demo
 
参考基本用法：
http://www.jianshu.com/p/b5e65490e7fe

https://www.jianshu.com/p/18230989a31b

添加依赖
dependencies {
  compile 'com.google.dagger:dagger:2.+'
  annotationProcessor 'com.google.dagger:dagger-compiler:2.+'
}

基本用法：
@Inject
作用一： 修饰需要注入依赖的对象
@Inject
Car car  
作用二：修饰构造器，这样dagger就会自动调用构造器去构造该对象， 但是module中的优先级要高于被@Inject修改的构造器（ps: 当需要一个对象的时候，先在module中找，module中没有，就去找该对象被@Inject修饰的构造器去创建一个对象）

public class Engine {
    public String name;
   @Inject
    public Engine(String name) {
        this.name = name;
    }
}

@Module     
 用于标注提供依赖的类。你可能会有点困惑，上面不是提到用@Inject标记构造函数就可以提供依赖了么，为什么还需要@Module？很多时候我们需要提供依赖的构造函数是第三方库的，我们没法给它加上@Inject注解，又比如说提供依赖的构造函数是带参数的，如果我们之所简单的使用@Inject标记它，那么他的参数又怎么来呢？@Module正是帮我们解决这些问题的。

@Component  
  	用于修饰注入组件
@Component(modules = {MakeCarModule.class}, dependencies = {AppComponent.class})
@PerMainActivity
public interface MakeCarComponent {
    void inject(MainActivity mainActivity);    //声明方要注入的方向
}

  @Qulifier
@Qulifier用于自定义注解，也就是说@Qulifier就如同Java提供的几种基本元注解一样用来标记注解类。我们在使用@Module来标注提供依赖的方法时，方法名我们是可以随便定义的（虽然我们定义方法名一般以provide开头，但这并不是强制的，只是为了增加可读性而已）。那么Dagger2怎么知道这个方法是为谁提供依赖呢？答案就是返回值的类型，Dagger2根据返回值的类型来决定为哪个被@Inject标记了的变量赋值。但是问题来了，一旦有多个一样的返回类型Dagger2就懵逼了。@Qulifier的存在正式为了解决这个问题，我们使用@Qulifier来定义自己的注解，然后通过自定义的注解去标注提供依赖的方法和依赖需求方（也就是被@Inject标注的变量），这样Dagger2就知道为谁提供依赖了。----一个更为精简的定义：当类型不足以鉴别一个依赖的时候，我们就可以使用这个注解标示；

@Scope
@Scope同样用于自定义注解，我能可以通过@Scope自定义的注解来限定注解作用域，实现局部的单例；

@Singleton：@Singleton其实就是一个通过@Scope定义的注解，我们一般通过它来实现全局单例。但实际上它并不能提前全局单例，是否能提供全局单例还要取决于对应的Component是否为一个全局对象。（ps： 和我们自定义的PerApp一个作用）


用法ps:
1、Component的接口，必须写一个无返回值的方法，方法参数必须为要注入到的类
如  void inject（MainActivity mainActivity）

2、dependencies的用法 （这也是dependencies和）
component中如果作为父类，必须在component接口中显式的声明，要为子类提供的依赖
如： AppModule中有    Context  providerContext（）方法
那么AppComponent中必须有 Context  providerContext（）方法
这样通过dependencies 依赖AppConponent来实现的子Component类才能获取到 AppComponent中提供的依赖

3、@Qualifier 限定符注解的用法
当module中有多个方法，都返回同一个类型的对象的时候，需要使用Qualifier注解自定义的注解加以区分
比如 :
一、首先自定义注解
@Qualifier
public @Interface  AppContext{}

@Qualifier 
public @Interface ActivityContext{}

二、在module中使用自定义的注解
@Module
public Class MainActivityModule{

@Provider
@AppContext
public Context getAppContext(){

}
@Provider
@ActivityContext
public Context getAppContext(){

}
}

三、在Activity中注入的时候使用自定义注解
@Inject
@ActivityContext
Context context;l

4、@Scope  作用域注解的使用
如果MainActiivty中有同一个类型的对象需要多次被注入
如:
@Inject
Car car;
@Inject
Car carTwo;

module中提供的对象，
@Provider
public Car getCar(){
return new Car();
}
这样car和carTow不是同一个对象，也就@Inject修饰一次Car类型对象，module就会new一次car对象，如果我们希望car和carTwo能够保持单例，也就是只new一次，那么我们可以使用Scope注解来自定义一个注解
使用流程：
一、使用Scope定义一个PerActiivty注解如：
@Scope
public @Interface  PerActiivty{}

二、在module中使用@PerActiivty
@Provider
@PerActiivty
public Car getCar(){
return new Car();
}

三、必须在Compont中声明@PerActiivty
@Component
@PerActiivty
public interface MainActivityComponent{	
void  inject (ManActiivty mainActiivty)
}

这样car和carTwo就是一个对象，实现了单例：
但是注意：  这个单例不是真正的单例，这个对象是存在于component中的，也就是如果这个component是唯一，才能保证这个对象唯一；
如果真正想要实现全局单例，那么这个对象必须声明到AppModule中，然后让AppCompnent保持唯一，再通过Scope自定义一个PerApp注解，用上这个注解；才能真正的保证对象实现全局单例；
ps：这个注解PerAcitiivty 和 PerApp没有自己的实际意义；他是在对应的Component中保持唯一的；所以PerApp修饰的依赖想要保持全局唯一，只能让AppComponent保持全局唯一，同时使用PerApp修饰

源码解析：
 	Dagger会自动生成一个 DaggerXxxxxComponent这样一个Component接口子类，在该类的inject方法中，进行了依赖注入；且该类的成员变量都是Module中对应提供依赖类型的工厂类Provider<T>
一个Module中的一个被@Provider修饰的方法返回T类型，自动生成了一个唯一和他对应的Factory<T>类或者Provider<T>类；这种类有一个get方法可以返回T
一个使用依赖注入的类，如MainActiivty，他对应生成一个 MainActiivty_MembesInjector类，其中每一个被@Inject修饰的对象T，对应MainActiivty_MembesInjector中的一个 
injectT(MainActiivty instance ， T t )j静态方法；用来将 T 对象注入到MainActivity中对应的对象；
ps：
提供依赖的是Provider<T>或者Factory<T> , 注入操作的是MainActiivty_MembesInjector中对应的injectT（）方法
 工厂类Factory<T>中的T对象，其实真正源自Module中的 @Provider修饰的  T  getT（）；方法，工厂类只不过是过了一个非空判断，如果为null，直接抛出非常状态依赖，如果不为null，直接返回；









