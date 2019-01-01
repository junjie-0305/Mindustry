package io.anuke.mindustry.entities.bullet;

import io.anuke.arc.math.geom.Rectangle;
import io.anuke.mindustry.content.fx.BulletFx;
import io.anuke.mindustry.entities.Units;
import io.anuke.arc.util.Time;

public abstract class FlakBulletType extends BasicBulletType{
    protected static Rectangle rect = new Rectangle();
    protected float explodeRange = 30f;

    public FlakBulletType(float speed, float damage){
        super(speed, damage, "shell");
        splashDamage = 15f;
        splashDamageRadius = 34f;
        hiteffect = BulletFx.flakExplosionBig;
        bulletWidth = 8f;
        bulletHeight = 10f;
    }

    @Override
    public void update(Bullet b){
        super.update(b);
        if(b.getData() instanceof Integer) return;

        if(b.timer.get(2, 6)){
            Units.getNearbyEnemies(b.getTeam(), rect.setSize(explodeRange*2f).setCenter(b.x, b.y), unit -> {
                if(b.getData() instanceof Float) return;

                if(unit.dst(b) < explodeRange){
                    b.setData(0);
                    Time.run(5f, () -> {
                        if(b.getData() instanceof Integer){
                            b.time(b.lifetime());
                        }
                    });
                }
            });
        }
    }
}
