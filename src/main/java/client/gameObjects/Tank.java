package client.gameObjects;
import client.components.*;
import client.components.tankDecorator.ITankDecorator;
import client.components.tankDecorator.HealthDecorator;
import client.components.tankDecorator.ShieldDecorator;
import client.components.weaponFacade.WeaponFacade;
import network.levelManagement.LevelManager;
import network.server.SEngine;

import java.awt.Color;

public class Tank extends GameObject implements ITankDecorator {    
    public int tankSize = 50;
    private double maxHP = 3;
    private double currentHP = 3;
    private double shieldAmount = 0;

    private HealthDecorator healthDecorator;
    private ShieldDecorator shieldDecorator;
    public Renderer renderer;
    public String originalImagePath;
    
    public Tank(String imagePath) {
        this(100, 100, 15, imagePath);
    }

    public Tank(Tank tank) {
        this(tank.imagePath);
    }

    public Tank(float x, float y, float angle, String imagePath) {
        addComponent(new Transform().setPosition(x, y).setRotation(angle));
        this.renderer = new Renderer(imagePath, this.tankSize, true);
        addComponent(this.renderer);
        addComponent(new ConstantSpeed(0.0f));
        addComponent(new ConstantRotation(0.0f));
        addComponent(new WeaponFacade(this.tankSize));
        addComponent(Collider.fromTexture(this.renderer));
        ((Collider)getComponent(Collider.Key())).setFunction(Colliders.tank);
        tag = Tag.Dynamic;
        this.healthDecorator = new HealthDecorator(this);
        this.shieldDecorator = new ShieldDecorator(this);
        this.originalImagePath = imagePath;
    }

    public double getShieldAmount() {
        return this.shieldAmount;
    }

    public double getMaxHP() {
        return this.maxHP;
    }

    public double getCurrentHP() {
        return this.currentHP;
    }

    public void setShield(double amount) {
        this.shieldAmount = amount;
    }

    public void setDamage(int damage) {
        currentHP -= damage;
        this.healthDecorator.decorate(this.currentHP, this.maxHP);
        if(currentHP <= 0) {
            LevelManager.getInstance().destroyPlayer(this);
        }
    }

    // Shield has toggle functionality for Decoration pattern testing.
    // This simulates shield ability activation.
    public void toggleShield() {
        if (getShieldAmount() > 0) {
            this.shieldDecorator.decorate(0); // set shield to 0
            this.healthDecorator.decorate(this.currentHP, this.maxHP);
        } else {
            this.shieldDecorator.decorate(1); // set shield to 1
            this.healthDecorator.decorate(this.currentHP + this.shieldAmount, this.maxHP, Color.CYAN);
        }
    }

    @Override public void decorate(String text) {}
    @Override public void decorate(double amount) {}
    @Override public void decorate(double a, double b) {}
    @Override public void decorate(double a, double b, Color color) {}
}
