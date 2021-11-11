package com.nick;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.*;
        import java.awt.image.BufferStrategy;

public class App extends Canvas implements Runnable {

    private int appWidth;
    private int appHeight;

    private JFrame frame;

    private Thread thread;
    private boolean running = false;

    private InteriorDesigner interiorDesigner;

    private int mouseX = 0;
    private int mouseY = 0;

    private MouseAdapter ML;
    private KeyAdapter KA;

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public App(int width, int height) {
        appWidth = width;
        appHeight = height;
        thread = new Thread(this);
        ML = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                interiorDesigner.onMouseClicked(e);
            }
        };

        KA = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                interiorDesigner.onKeyPressed(e);
            }
        };
    }

    public void init() {

        //Initialise a new simulator
        interiorDesigner = new InteriorDesigner(appWidth, appHeight, this);

        //Set the canvas properties
        setMaximumSize(new Dimension(appWidth, appHeight));
        setMinimumSize(new Dimension(appWidth, appHeight));
        setPreferredSize(new Dimension(appWidth, appHeight));
        setBackground(Color.white);

        //Set the frame properties
        frame = new JFrame("Interior Designer");
        this.addMouseMotionListener(ML);
        this.addMouseListener(ML);
        this.addKeyListener(KA);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    //Start this class' thread
    public synchronized void start() {
        if(running) {
            return;
        }
        thread.start();
        running = true;
    }

    //Stop this class' thread
    public synchronized void stop() {
        if(!running) {
            return;
        }
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        running = false;
    }

    //Update variables of everything in the simulator
    public void tick() {
        interiorDesigner.tick();
    }

    public void render() {

        BufferStrategy bs = getBufferStrategy();

        if(bs == null) {
            createBufferStrategy(3);
            bs = getBufferStrategy();
        }

        Graphics g = bs.getDrawGraphics();

        //Clear the screen ready to redraw the next frame
        g.clearRect(0,0, appWidth, appHeight);

        //Paints background
        super.paint(g);

        //Render everything in simulator to the screen
        interiorDesigner.render(g);

        bs.show();
        g.dispose();

    }

    @Override
    public void run() {

        init();

        //game loop

        double fps = 60;
        double delta = 0;
        double timePerTick = 1000000000/fps;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(running) {

            now = System.nanoTime();
            delta += (now-lastTime)/timePerTick;
            timer += now-lastTime;
            lastTime = now;
            if(delta >= 0) {
                tick();
                render();
                ticks++;
                delta--;
            }

            //Update the number of ticks every second to the frame title
            if(timer >= 1000000000) {
                frame.setTitle("Interior Designer (FPS: " + ticks + ")");
                timer = 0;
                ticks = 0;
            }

        }

        stop();

    }
}
