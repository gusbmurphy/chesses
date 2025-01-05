package com.gusmurphy.chesses;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private ShapeRenderer shapeRenderer;
    private FitViewport viewport;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        viewport = new FitViewport(8, 5);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.line(1, 1, 3, 4);
        shapeRenderer.rect(0.5f, 3f, 4, 5);
        shapeRenderer.circle(3, 4, 2);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.rect(5, 5, 2, 3);
        shapeRenderer.circle(4, 0, 3);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

}
