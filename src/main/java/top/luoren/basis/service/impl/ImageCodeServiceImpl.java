package top.luoren.basis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.luoren.basis.entity.ImageCode;
import top.luoren.basis.mapper.ImageCodeMapper;
import top.luoren.basis.service.ImageCodeService;

/**
 * @author luoren
 * @date 2019-05-06 11:34
 */
@Service
public class ImageCodeServiceImpl extends ServiceImpl<ImageCodeMapper, ImageCode> implements ImageCodeService {
}
