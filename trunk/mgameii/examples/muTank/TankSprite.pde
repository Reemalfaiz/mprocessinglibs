/**
 * This class is based on the Article Creating 2D Action Games with the Game API
 * http://developers.sun.com/techtopics/mobility/midp/articles/game/index.html
 *
 * $Id: TankSprite.pde 202 2007-04-19 18:05:02Z marlonj $
 */

class TankSprite extends MSprite
{
  int mDirection;
  int mKX, mKY;

  int mLastDelta;
  boolean mLastWasTurn;

  int[] kTransformLookup = {
    TRANS_NONE, TRANS_NONE, TRANS_NONE,
    TRANS_MIRROR_ROT90,
    TRANS_ROT90, TRANS_ROT90, TRANS_ROT90,
    TRANS_MIRROR_ROT180,
    TRANS_ROT180, TRANS_ROT180, TRANS_ROT180,
    TRANS_MIRROR_ROT270,
    TRANS_ROT270, TRANS_ROT270, TRANS_ROT270,
    TRANS_MIRROR
  };

  int[] kFrameLookup = {
    0, 1, 2, 1,
    0, 1, 2, 1,
    0, 1, 2, 1,
    0, 1, 2, 1
  };

  int[] kCosLookup = {
    0,  383,  707,  924,
    1000,  924,  707,  383,
    0, -383, -707, -924,
    -1000, -924, -707, -383
  };

  int[] kSinLookup = {
    1000,  924,  707,  383,
    0, -383, -707, -924,
    -1000, -924, -707, -383,
    0,  383,  707,  924
  };

  public TankSprite(PImage image, int frameWidth, int frameHeight)
  {
    super(image, frameWidth, frameHeight);
    defineReferencePixel(frameWidth / 2, frameHeight / 2);
    mDirection = 0;
  }

  void turn(int delta)
  {
    mDirection += delta;
    if (mDirection < 0) mDirection += 16;
    if (mDirection > 15) mDirection %= 16;

    setFrame(kFrameLookup[mDirection]);
    setTransform(kTransformLookup[mDirection]);

    mLastDelta = delta;
    mLastWasTurn = true;
  }

  void forward(int delta)
  {
    fineMove(kCosLookup[mDirection] * delta,
    -kSinLookup[mDirection] * delta);
    mLastDelta = delta;
    mLastWasTurn = false;
  }

  public void undo()
  {
    if (mLastWasTurn)
      turn(-mLastDelta);
    else
      forward(-mLastDelta);
  }

  private void fineMove(int kx, int ky)
  {
    // First initialize mKX and mKY if they're
    // not close enough to the actual x and y.
    int x = getX();
    int y = getY();
    int errorX = Math.abs(mKX - x * 1000);
    int errorY = Math.abs(mKY - y * 1000);
    if (errorX > 1000 || errorY > 1000) {
      mKX = x * 1000;
      mKY = y * 1000;
    }
    // Now add the deltas.
    mKX += kx;
    mKY += ky;
    // Set the actual position.
    setPosition(mKX / 1000, mKY / 1000);
  }
}
