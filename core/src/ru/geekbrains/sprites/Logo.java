package ru.geekbrains.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.exception.GameException;
import ru.geekbrains.math.Rect;

public class Logo extends Sprite {

    private static final float V_LEN = 0.01f;

    private Vector2 touch;
    private Vector2 v;
    private Vector2 tmp;

    public Logo(Texture texture) throws GameException {
        super(new TextureRegion(texture));
        touch = new Vector2();
        v = new Vector2();
        tmp = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.3f);
    }

    @Override
    public void update(float delta) {
        tmp.set(touch);
        if (tmp.sub(pos).len() > V_LEN) {
            pos.add(v);
        } else {
            pos.set(touch);
            v.setZero();
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch = touch;
        v.set(touch.cpy().sub(pos)).setLength(V_LEN);
        return false;
    }
}
