package top.totoro.swing.widget.util;

import top.totoro.swing.widget.base.Location;
import top.totoro.swing.widget.base.Size;
import top.totoro.swing.widget.context.Activity;
import top.totoro.swing.widget.view.View;

import java.awt.*;

import static java.lang.Thread.sleep;

/**
 * 一些动画工具
 */
public class AnimateUtil {
    public enum Direction {
        LTR,    // 从左到右
        RTL,    // 从右到左
        TTB,    // 从上到下
        BTT     // 从下到上
    }

    public static void fadeOut(View<?, ?> target, Direction direction, float seconds, Runnable... then) {
        Location location = Location.getLocation(target.getComponent());
        Size size = Size.getSize(target.getComponent());
        if (size == null) {
            if (then != null && then.length > 0)
                then[0].run();
            return;
        }
        switch (direction) {
            case LTR:
                break;
            case RTL:
                new Thread(() -> {
                    // 淡出过程的帧数
                    int fadeFps = (int) (seconds * 15);
                    // 每一帧宽度的缩减
                    int widthOutFps = size.width / fadeFps + 1;
                    for (int i = 0; i < fadeFps; i++) {
                        size.width -= widthOutFps;
                        target.setSize(size.width, size.height);
                        target.invalidateSuper();
                        pause(20);
                    }
                    if (then != null && then.length > 0)
                        then[0].run();
                }).start();
                break;
            case TTB:
            case BTT:
        }
    }

    public static void fadeOut(Activity target, Direction direction, float seconds, Runnable... then) {
        Location location = Location.getLocation(target.getFrame());
        Size size = Size.getSize(target.getFrame());
        if (size == null) {
            if (then != null && then.length > 0)
                then[0].run();
            return;
        }
        switch (direction) {
            case LTR:
                break;
            case RTL:
                new Thread(() -> {
                    // 淡出过程的帧数
                    int fadeFps = (int) (seconds * 15);
                    // 每一帧宽度的缩减
                    int widthOutFps = size.width / fadeFps + 1;
                    for (int i = 0; i < fadeFps; i++) {
                        size.width -= widthOutFps;
                        target.resetSize(size.width, size.height);
                        pause(20);
                    }
                    if (then != null && then.length > 0)
                        then[0].run();
                }).start();
                break;
            case TTB:
            case BTT:
        }
    }

    public static void fadeIn(Activity target, Direction direction, float seconds, Size targetSize, Runnable... then) {
        Location location = Location.getLocation(target.getFrame());
        Size size = Size.getSize(target.getFrame());
        if (targetSize == null || size == null) {
            if (then != null && then.length > 0)
                then[0].run();
            return;
        }
        switch (direction) {
            case LTR:
                new Thread(() -> {
                    // 淡入过程的帧数
                    int fadeFps = (int) (seconds * 15);
                    // 每一帧宽度的增加
                    int widthInFps = (targetSize.width - size.width) / fadeFps + 1;
                    for (int i = 0; i < fadeFps; i++) {
                        size.width += widthInFps;
                        target.resetSize(size.width, size.height);
                        pause(20);
                    }
                    if (then != null && then.length > 0)
                        then[0].run();
                }).start();
                break;
            case RTL:
                break;
            case TTB:
            case BTT:
        }
    }

    public static void zoomOut(View<?, ?> target, float seconds, Runnable... then) {
        Location location = Location.getLocation(target.getComponent());
        Size size = Size.getSize(target.getComponent());
        if (size == null) {
            if (then != null && then.length > 0)
                then[0].run();
            return;
        }
        new Thread(() -> {
            // 淡出过程的帧数
            int fadeFps = (int) (seconds * 15);
            // 每一帧宽度的缩减
            int widthOutFps = size.width / fadeFps + 1;
            // 每一帧宽度的缩减
            int heightOutFps = size.width / fadeFps + 1;
            for (int i = 0; i < fadeFps; i++) {
                size.width -= widthOutFps;
                size.height -= heightOutFps;
                target.setSize(size.width, size.height);
                target.invalidateSuper();
                pause(20);
            }
            if (then != null && then.length > 0)
                then[0].run();
        }).start();
    }

    public static void zoomOut(Activity target, float seconds, Runnable... then) {
        Location location = Location.getLocation(target.getFrame());
        Size size = Size.getSize(target.getFrame());
        if (size == null) {
            if (then != null && then.length > 0)
                then[0].run();
            return;
        }
        new Thread(() -> {
            // 淡出过程的帧数
            int fadeFps = (int) (seconds * 15);
            // 每一帧宽度的缩减
            int widthOutFps = size.width / fadeFps + 1;
            // 每一帧宽度的缩减
            int heightOutFps = size.width / fadeFps + 1;
            for (int i = 0; i < fadeFps; i++) {
                size.width -= widthOutFps;
                size.height -= heightOutFps;
                target.resetSize(size.width, size.height);
                pause(20);
            }
            if (then != null && then.length > 0)
                then[0].run();
        }).start();
    }

    public static void zoomIn(View<?, ?> target, Size targetSize, float seconds, Runnable... then) {
        Location location = Location.getLocation(target.getComponent());
        Size size = Size.getSize(target.getComponent());
        if (targetSize == null || size == null) {
            if (then != null && then.length > 0)
                then[0].run();
            return;
        }
        new Thread(() -> {
            // 淡出过程的帧数
            int fadeFps = (int) (seconds * 15);
            // 每一帧宽度的缩减
            int widthOutFps = (targetSize.width - size.width) / fadeFps + 1;
            // 每一帧宽度的缩减
            int heightOutFps = (targetSize.height - size.height) / fadeFps + 1;
            for (int i = 0; i < fadeFps; i++) {
                size.width += widthOutFps;
                size.height += heightOutFps;
                target.setSize(size.width, size.height);
                target.invalidateSuper();
                pause(20);
            }
            if (then != null && then.length > 0)
                then[0].run();
        }).start();
    }

    public static void zoomIn(Activity target, Size targetSize, float seconds, Runnable... then) {
        Location location = Location.getLocation(target.getFrame());
        Size size = Size.getSize(target.getFrame());
        if (targetSize == null || size == null) {
            if (then != null && then.length > 0)
                then[0].run();
            return;
        }
        new Thread(() -> {
            // 淡出过程的帧数
            int fadeFps = (int) (seconds * 15);
            // 每一帧宽度的缩减
            int widthOutFps = (targetSize.width - size.width) / fadeFps + 1;
            // 每一帧宽度的缩减
            int heightOutFps = (targetSize.height - size.height) / fadeFps + 1;
            for (int i = 0; i < fadeFps; i++) {
                size.width += widthOutFps;
                size.height += heightOutFps;
                target.resetSize(size.width, size.height);
                pause(20);
            }
            if (then != null && then.length > 0)
                then[0].run();
        }).start();
    }

    /**
     * 渐淡动画，透明度在一段时间内变为0
     *
     * @param target  目标窗口
     * @param seconds 动画时长（s）
     * @param then    动画结束之后的操作，也是在线程中执行
     */
    public static void fadeAway(Activity target, float seconds, Runnable... then) {
        new Thread(() -> {
            // 淡出过程的帧数
            int fadeFps = (int) (seconds * 15);
            // 每一帧不透明度的增幅
            float opacityOutFps = 1f / (float) fadeFps;
            float opacity = 1; // 当前的不透明度
            for (int i = 0; i < fadeFps && opacity > 0; i++) {
                opacity -= opacityOutFps;
                target.getFrame().setOpacity(opacity < 0 ? 0 : opacity);
                pause(20);
            }
            if (then != null && then.length > 0)
                then[0].run();
        }).start();
    }

    /**
     * 渐入动画，透明度在一段时间内变为1
     *
     * @param target  目标窗口
     * @param seconds 动画时长（s）
     * @param then    动画结束之后的操作，也是在线程中执行
     */
    public static void fadeCome(Activity target, float seconds, Runnable... then) {
        new Thread(() -> {
            // 淡出过程的帧数
            int fadeFps = (int) (seconds * 15);
            // 每一帧不透明度的减幅
            float opacityInFps = 1f / (float) fadeFps;
            float opacity = 0; // 当前的不透明度
            for (int i = 0; i < fadeFps && opacity < 1; i++) {
                opacity += opacityInFps;
                target.getFrame().setOpacity(opacity > 1 ? 1 : opacity);
                pause(20);
            }
            if (then != null && then.length > 0)
                then[0].run();
        }).start();
    }

    private static void pause(long time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}