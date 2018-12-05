官方依赖
https://github.com/google/dagger

参考github
 https://github.com/tongchexinfeitao/Dragger2Sample
ps：Sample展示了 依赖的方式和集成的方式：
其中MyParentComponent 和 MySubComponnet 展示的是继承关系，安卓中推荐使用继承关系
AppComponent和MakeCarDependeciesComponent 展示的是依赖关系

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

@Provides
用于标注Module所标注的类中的方法，该方法在需要提供依赖时被调用，从而把预先提供好的对象当做依赖给标注了@Inject的变量赋值；

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

@Singleton （其实就是一个Scope自定义的注解，没有实际意义）
@Singleton其实就是一个通过@Scope定义的注解，我们一般通过它来实现全局单例。但实际上它并不能提前全局单例，是否能提供全局单例还要取决于对应的Component是否为一个全局对象。（ps： 和我们自定义的PerApp一个作用）


用法ps:
1、Component的接口，必须写一个无返回值的方法，方法参数必须为要注入到的类
 如： void inject（MainActivity mainActivity）

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

@Provides
@AppContext
public Context getAppContext(){

}
@Provides
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
@Provides
public Car getCar(){
return new Car();
}
这样car和carTow不是同一个对象，也就@Inject修饰一次Car类型对象，module就会new一次car对象，如果我们希望car和carTwo能够保持单例，也就是只new一次，那么我们可以使用Scope注解来自定义一个注解
使用流程：
一、使用Scope定义一个PerActiivty注解如：
@Scope
public @Interface  PerActiivty{}

二、在module中使用@PerActiivty
@Provides
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

其他进阶用法：
@BindsInstance 注解的用法
相对于写一个带有构造函数带有参数的 Module，优先使用@BindsInstance方法，@BindInstance注解修饰的方法，相当于Module中的@Provides修饰的方法，都可以提供依赖

@Component
public interface MainActivityComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder car(Car  car);
        HomeActivityComponent build();
    }
}

Lazy （延迟注入）
有时我们想注入的依赖在使用时再完成初始化，加快加载速度，就可以使用注入Lazy<T>。只有在调用 Lazy<T> 的 get() 方法时才会初始化依赖实例注入依赖。
public class Man {
    @Inject
    Lazy<Car> lazyCar;

    public void goWork() {
        ...
        lazyCar.get(); // lazyCar.get() 返回 Car 实例
        ...
    }
}


Provider 注入
有时候不仅仅是注入单个实例，我们需要多个实例，这时可以使用注入Provider<T>，每次调用它的 get() 方法都会调用到 @Inject 构造函数创建新实例或者 Module 的 provide 方法返回实例。
public class CarFactory {
    @Inject
    Provider<Car> carProvider;

    public List<Car> makeCar(int num) {
        ...
        List<Car> carList = new ArrayList<Car>(num);
        for (int i = 0; i < num; i ++) {
            carList.add(carProvider.get());
        }
        return carList;
    }
}



总结
Qualifier 限定符用来解决依赖迷失问题，可以依赖实例起个别名用来区分。
Scope 作用域的本质是 Component 会持有与之绑定的依赖实例的引用，要想确保实例的生命周期，关键在于控制 Component 的生命周期。

依赖关系：
使用Dependencies的方式也就是依赖关系
关于dependencies用法：
1.没有@scope的component不能依赖有@scope的component
2.component的dependencies与component自身的@scope不能相同，即组件之间的@scope必须不同

用法：
第一、在父component中必须显示声明可供子类使用的依赖，子类才可以使用这些依赖
@Component(modules = {AppModule.class})
public interface AppComponent {
    //在父component中必须显示声明，子类才可以使用这些依赖
    Context provideAppContext();
    OkHttpClient provideOkHttpClient();
    UserBean provideUserBean();
}

第二、子Componnet中必须使用dependencies 属性
@Component(modules = {MakeCarModule.class}, dependencies = {AppComponent.class})
@PerMainActivity
public interface MakeCarComponent {
    void inject(MainActivity mainActivity);   

}


继承关系：
安卓中推荐使用SubConmponents的方式也就是继承关系
用法：
一、父Component的Mudule类中需要声明子Component
@Module(subcomponents = SonComponent.class)
public class CarModule {
    @Provides
    @ManScope
    static Car provideCar() {
        return new Car();
    }
}

二、父Component ，中需要有一个方法生成 子Component.Builder 对象（而不需要显式声明，父类中提供给子类的依赖，默认全部集成）
@ManScope
@Component(modules = CarModule.class)
public interface ManComponent {
    void injectMan(Man man);

    SonComponent.Builder sonComponent();    // 用来创建 Subcomponent
}

三、子Component 必须显式地声明 Subcomponent.Builder，因为父Component 需要用 Builder 来创建 SubComponent
@SonScope
@SubComponent(modules = BikeModule.class)
public interface SonComponent {
    void inject(Son son);

    @Subcomponent.Builder
    interface Builder { 
        SonComponent build();
    }
}
四、构造一个子Component, 先获取 SubComponent.Builder 方法获取 SubComponent 实例。
ManComponent parentComponent = DaggerManComponent.builder()
    .build();

SonComponent sonComponent = parentComponent .sonComponent()
    .build();
sonComponent.inject(son);


依赖关系 vs 继承关系
相同点：
两者都能复用其他 Component 的依赖
有依赖关系和继承关系的 Component 不能有相同的 Scope
区别：
依赖关系中被依赖的 Component 必须显式地提供公开依赖实例的接口，而 SubComponent 默认继承 parent Component 的依赖。
依赖关系会生成两个独立的 DaggerXXComponent 类，而 SubComponent 不会生成 独立的 DaggerXXComponent 类。
在 Android 开发中，Activity 是 App 运行中组件，Fragment 又是 Activity 一部分，这种组件化思想适合继承关系，所以在 Android 中一般使用 SubComponent。
