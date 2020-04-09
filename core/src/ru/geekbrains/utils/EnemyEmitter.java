package ru.geekbrains.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprites.Enemy;

public class EnemyEmitter {

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_SMALL_HP = 1;

    private Rect worldBounds;
    private Sound shootSound;
    private TextureRegion bulletRegion;

    private float generateInterval = 4f;
    private float generateTimer;

    private final TextureRegion[] enemySmallRegion;

    private final Vector2 enemySmallV;

    private final EnemyPool enemyPool;

    public EnemyEmitter(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds, Sound shootSound) {
        this.worldBounds = worldBounds;
        this.shootSound = shootSound;
        this.enemyPool = enemyPool;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        TextureRegion enemy0 = atlas.findRegion("enemy0");
        this.enemySmallRegion = Regions.split(enemy0, 1, 2, 2);
        this.enemySmallV = new Vector2(0, -0.2f);
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            float type = (float) Math.random();
            if (type < 0.6f) {
                enemy.set(
                        enemySmallRegion,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY,
                        ENEMY_SMALL_DAMAGE,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        shootSound,
                        ENEMY_SMALL_HP,
                        ENEMY_SMALL_HEIGHT
                );
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
