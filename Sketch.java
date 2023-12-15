import processing.core.PApplet;

/**
 * A simple game where the player controls a character to avoid falling snowflakes.
 */
public class Sketch extends PApplet {

  float[] snowX;
  float[] snowY;
  float[] ySpeed;
  boolean[] ballHideStatus;
  int numSnowflakes = 10;
  int snowRadius = 20;

  float playerX = width / 2;
  float playerY;
  int playerLives = 3;
  int playerRadius = 20;
  boolean keyA, keyD;

  int score;

  /**
   * Sets the size of the sketch.
   */
  public void settings() {
    size(400, 400);
  }

  /**
   * Initializes the snowfall and other game elements.
   */
  public void setup() {
    setupSnowfall();
  }

  /**
   * Draws the game elements on the screen.
   */
  public void draw() {
    background(75, 75, 75);
    drawSnowfall();
    drawInfo();
    playerMovement();
    drawPlayer();
  }

  /**
   * Initializes the parameters for the falling snowflakes.
   */
  public void setupSnowfall() {
    snowX = new float[numSnowflakes];
    snowY = new float[numSnowflakes];
    ySpeed = new float[numSnowflakes];
    ballHideStatus = new boolean[numSnowflakes];

    for (int i = 0; i < numSnowflakes; i++) {
      snowX[i] = random(width);
      snowY[i] = random(-200, 0);
      ySpeed[i] = 2;
      ballHideStatus[i] = false;
    }
  }

  /**
   * Draws the falling snowflakes and updates their positions.
   */
  public void drawSnowfall() {
    noStroke();
    fill(255);

    for (int i = 0; i < numSnowflakes; i++) {

      if (ballHideStatus[i]) {
        snowX[i] = random(width - 100);
        snowY[i] = random(-200, 0);
      }

      if (!ballHideStatus[i]) {
        snowY[i] += ySpeed[i];

        if (snowY[i] > height) {
          snowX[i] = random(width);
          snowY[i] = random(-200, 0);
        }

        if (keyCode == UP && ySpeed[i] < 10) {
          ySpeed[i]++;
          delay(60);
        } else if (keyCode == DOWN && ySpeed[i] > 1)  {
          ySpeed[i]--;
          delay(60);
        }

        if (dist(snowX[i], snowY[i], playerX, playerY) < playerRadius) {
          playerLives--;
          ballHideStatus[i] = true;
        }

        if (!ballHideStatus[i]) {
          ellipse(snowX[i], snowY[i], snowRadius, snowRadius);
        }
      }
    }
  }

  /**
   * Draws the player's information, such as lives and score.
   */
  public void drawInfo() {

    for (int i = 0; i < playerLives; i++) {
      stroke(0);
      fill(255, 0, 0);
      rect(width - 35 - i * 30, 10, 30, 10);
    }

    noFill();
    rect(width - 95, 10, 30, 10);
    rect(width - 65, 10, 30, 10);
    rect(width - 35, 10, 30, 10);

    if (frameCount % 60 == 0 && playerLives > 0) {
      score++;
    }

    if (playerLives <= 0) {
      background(255);
      textSize(24);
      fill(0);
      textAlign(CENTER, CENTER);
      text("Game Over", width / 2, height / 2 - 24);
      textSize(16);
      fill(0);
      text("Score: " + score, width / 2, height / 2);
    }
  }

  /**
   * Draws the player character.
   */
  public void drawPlayer() {
    playerY = height - playerRadius;
    playerX = constrain(playerX, playerRadius, width - playerRadius);
    fill(0, 0, 255);

    if (playerLives > 0) {
      ellipse(playerX, playerY, playerRadius, playerRadius);
    }
  }

  /**
   * Handles mouse clicks to interact with falling snowflakes.
   */
  public void mouseClicked() {
    for (int i = 0; i < numSnowflakes; i++) {
      if (mouseX > snowX[i] - snowRadius && mouseX < snowX[i] + snowRadius
          && mouseY > snowY[i] - snowRadius && mouseY < snowY[i] + snowRadius) {
        ballHideStatus[i] = true;
      }
    }
  }

  /**
   * Handles key presses for player movement.
   */
  public void keyPressed() {
    if (key == 'a') {
      keyA = true;
    } else if (key == 'd') {
      keyD = true;
    }
  }

  /**
   * Handles key releases for player movement.
   */
  public void keyReleased() {
    if (key == 'a') {
      keyA = false;
    } else if (key == 'd') {
      keyD = false;
    }
  }

  /**
   * Updates the player's position based on key inputs.
   */
  public void playerMovement() {
    if (keyA) {
      playerX -= 5;
    }
    if (keyD) {
      playerX += 5;
    }
  }
}
