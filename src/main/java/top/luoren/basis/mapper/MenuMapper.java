package top.luoren.basis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.luoren.basis.entity.Menu;

import java.util.Set;

/**
 * @author luoren
 * @date 2019-04-28 12:22
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 获取所有的菜单
     * @return
     */
    Set<Menu> getAllMenu();
}
