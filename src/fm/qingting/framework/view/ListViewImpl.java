package fm.qingting.framework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ListView;
import fm.qingting.framework.event.IEventHandler;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ListViewImpl extends ListView
  implements IListView
{
  protected boolean activate = false;
  private int alpha = 255;
  protected Animation.AnimationListener animationListener = new Animation.AnimationListener()
  {
    public void onAnimationEnd(Animation paramAnonymousAnimation)
    {
      if (paramAnonymousAnimation == ListViewImpl.this.openAnimation)
      {
        ListViewImpl.this.isOpened = true;
        ListViewImpl.this.onViewDidOpen();
      }
      while (paramAnonymousAnimation != ListViewImpl.this.closeAnimation)
        return;
      ListViewImpl.this.isOpened = false;
      ListViewImpl.this.onViewDidClose();
    }

    public void onAnimationRepeat(Animation paramAnonymousAnimation)
    {
    }

    public void onAnimationStart(Animation paramAnonymousAnimation)
    {
      if (paramAnonymousAnimation == ListViewImpl.this.openAnimation)
      {
        ListViewImpl.this.onViewWillOpen();
        ListViewImpl.this.isOpened = false;
      }
      while (paramAnonymousAnimation != ListViewImpl.this.closeAnimation)
        return;
      ListViewImpl.this.onViewWillClose();
      ListViewImpl.this.isOpened = false;
    }
  };
  protected int animationRetain = 0;
  protected Point centerPoint = new Point(0, 0);
  protected Animation closeAnimation;
  protected IEventHandler eventHandler;
  private boolean hasAlpha = false;
  private boolean hasScaled = false;
  protected boolean isOpened = true;
  protected Animation openAnimation;
  private float scale = 1.0F;
  private Set<WeakReference<IViewEventListener>> viewEventListeners = new HashSet();

  public ListViewImpl(Context paramContext)
  {
    super(paramContext);
  }

  public ListViewImpl(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public ListViewImpl(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public void addViewEventListener(IViewEventListener paramIViewEventListener)
  {
    removeViewEventListener(paramIViewEventListener);
    this.viewEventListeners.add(new WeakReference(paramIViewEventListener));
  }

  public void close(boolean paramBoolean)
  {
    if ((paramBoolean) && (this.closeAnimation != null))
    {
      startAnimation(this.closeAnimation);
      return;
    }
    onViewWillClose();
    this.isOpened = false;
    onViewDidClose();
  }

  protected void dispatchActionEvent(String paramString, Object paramObject)
  {
    if (this.eventHandler != null)
      this.eventHandler.onEvent(this, paramString, paramObject);
  }

  protected void dispatchDraw(Canvas paramCanvas)
  {
    if ((!this.hasAlpha) && (this.alpha != 255))
      paramCanvas.saveLayerAlpha(new RectF(paramCanvas.getClipBounds()), this.alpha, 31);
    while (true)
    {
      if ((!this.hasScaled) && (this.scale != 1.0F))
        paramCanvas.scale(this.scale, this.scale, this.centerPoint.x, this.centerPoint.y);
      super.dispatchDraw(paramCanvas);
      paramCanvas.restore();
      return;
      paramCanvas.save();
    }
  }

  protected void dispatchViewEvent(int paramInt)
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.addAll(this.viewEventListeners);
    Iterator localIterator = localArrayList2.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        this.viewEventListeners.removeAll(localArrayList1);
        return;
      }
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      IViewEventListener localIViewEventListener = (IViewEventListener)localWeakReference.get();
      if (localIViewEventListener == null)
        localArrayList1.add(localWeakReference);
      else
        switch (paramInt)
        {
        default:
          break;
        case 0:
          localIViewEventListener.viewWillOpen(this);
          break;
        case 1:
          localIViewEventListener.viewDidOpened(this);
          break;
        case 2:
          localIViewEventListener.viewWillClose(this);
          break;
        case 3:
          localIViewEventListener.viewDidClosed(this);
        }
    }
  }

  public void draw(Canvas paramCanvas)
  {
    this.hasScaled = false;
    this.hasAlpha = false;
    if (this.alpha != 255)
    {
      this.hasAlpha = true;
      paramCanvas.saveLayerAlpha(new RectF(paramCanvas.getClipBounds()), this.alpha, 31);
    }
    while (true)
    {
      if (this.scale != 1.0F)
      {
        this.hasScaled = true;
        paramCanvas.scale(this.scale, this.scale, this.centerPoint.x, this.centerPoint.y);
      }
      super.draw(paramCanvas);
      paramCanvas.restore();
      this.hasScaled = false;
      return;
      paramCanvas.save();
    }
  }

  public boolean getActivate()
  {
    return this.activate;
  }

  public Point getCenterPoint()
  {
    return new Point(this.centerPoint.x, this.centerPoint.y);
  }

  public int getQtAlpha()
  {
    return this.alpha;
  }

  public float getScale()
  {
    return this.scale;
  }

  public Object getValue(String paramString, Object paramObject)
  {
    return null;
  }

  public View getView()
  {
    return this;
  }

  public boolean isAnimating()
  {
    return (this.animationRetain > 0) || ((getAnimation() != null) && (!getAnimation().hasEnded()));
  }

  public boolean isOpened()
  {
    return this.isOpened;
  }

  protected void onViewDidClose()
  {
    dispatchViewEvent(3);
  }

  protected void onViewDidOpen()
  {
    dispatchViewEvent(1);
  }

  protected void onViewWillClose()
  {
    dispatchViewEvent(2);
  }

  protected void onViewWillOpen()
  {
    dispatchViewEvent(0);
  }

  public void open(boolean paramBoolean)
  {
    if ((paramBoolean) && (this.openAnimation != null))
    {
      startAnimation(this.openAnimation);
      return;
    }
    onViewWillOpen();
    this.isOpened = true;
    onViewDidOpen();
  }

  public void removeViewEventListener(IViewEventListener paramIViewEventListener)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.viewEventListeners.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        this.viewEventListeners.removeAll(localArrayList);
        return;
      }
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      IViewEventListener localIViewEventListener = (IViewEventListener)localWeakReference.get();
      if ((localIViewEventListener == null) || (localIViewEventListener == paramIViewEventListener))
        localArrayList.add(localWeakReference);
    }
  }

  public void setActivate(boolean paramBoolean)
  {
    if (this.activate == paramBoolean)
      return;
    this.activate = paramBoolean;
    setFocusable(paramBoolean);
    setFocusableInTouchMode(paramBoolean);
    setEnabled(paramBoolean);
    if (!paramBoolean)
    {
      clearFocus();
      return;
    }
    requestFocus();
  }

  public void setCloseAnimation(Animation paramAnimation)
  {
    this.closeAnimation = paramAnimation;
    this.closeAnimation.setAnimationListener(this.animationListener);
  }

  public void setEventHandler(IEventHandler paramIEventHandler)
  {
    this.eventHandler = paramIEventHandler;
  }

  public void setOpenAnimation(Animation paramAnimation)
  {
    this.openAnimation = paramAnimation;
    this.openAnimation.setAnimationListener(this.animationListener);
  }

  public void setQtAlpha(int paramInt)
  {
    if (this.alpha != paramInt)
    {
      this.alpha = paramInt;
      invalidate();
    }
  }

  public void setScale(float paramFloat)
  {
    if (this.scale != paramFloat)
    {
      this.scale = paramFloat;
      invalidate();
    }
  }

  public void update(String paramString, Object paramObject)
  {
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.framework.view.ListViewImpl
 * JD-Core Version:    0.6.2
 */