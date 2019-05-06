package top.luoren.basis;

import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.luoren.basis.entity.ImageCode;
import top.luoren.basis.entity.User;
import top.luoren.basis.mapper.UserMapper;
import top.luoren.basis.service.ImageCodeService;
import top.luoren.basis.service.UserService;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysCoreModuleApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    ImageCodeService imageCodeService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testInsert() {
        System.out.println("-----insert method test-----");
        User user=new User();
        user.setName("test2");
        user.setPassword("123456");
        user.setAge(22);
        user.setEmail("test2@top.com");
        int rowNum=userMapper.insert(user);
        Assert.assertEquals(1,rowNum);
    }

    @Test
    public void testSelect(){
        System.out.println("-----select method test-----");
        List<User> users=userMapper.selectList(null);
        Assert.assertEquals(2,users.size());
        users.forEach(System.out::println);
    }

    @Test
    public void testSelect2(){
        System.out.println("-----select method test2-----");
        Map<String,String> map= Maps.newHashMap();
        map.put("key","t2");
        List<User> users=userService.listUser(map);
        Assert.assertEquals(1,users.size());
        users.forEach(System.out::println);
    }

    /**
     * 查询验证码
     */
    @Test
    public void test3() {
        ImageCode imageCode = imageCodeService.getById("392ce391-e074-4850-a396-21bd4b22fc87");
        System.out.println(imageCode);
    }

}
