package fm.qingting.qtradio.view.playview_bb;

import android.content.Context;
import android.text.Layout.Alignment;
import android.view.View.MeasureSpec;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.BroadcasterNode;
import fm.qingting.qtradio.model.ProgramNode;
import java.util.List;

public class DanmakuPlayInfoView extends QtView
{
  private TextViewElement mTitleElement;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 60, 720, 120, 0, 0, ViewLayout.FILL);
  private final ViewLayout titleLayout = this.standardLayout.createChildLT(700, 60, 10, 0, ViewLayout.SCALE_FLAG_SLTCW);

  public DanmakuPlayInfoView(Context paramContext)
  {
    super(paramContext);
    this.mTitleElement = new TextViewElement(paramContext);
    this.mTitleElement.setMaxLineLimit(1);
    this.mTitleElement.setColor(SkinManager.getTextColorNormal_New());
    this.mTitleElement.setText("祝您开心每一天！", false);
    this.mTitleElement.setAlignment(Layout.Alignment.ALIGN_CENTER);
    addElement(this.mTitleElement);
  }

  private String getBroadCasters(List<BroadcasterNode> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0))
      return "";
    StringBuilder localStringBuilder = new StringBuilder("主播");
    for (int i = 0; i < paramList.size(); i++)
      localStringBuilder.append(" " + ((BroadcasterNode)paramList.get(i)).nick);
    return localStringBuilder.toString();
  }

  public void close(boolean paramBoolean)
  {
    super.close(paramBoolean);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.titleLayout.scaleToBounds(this.standardLayout);
    this.mTitleElement.measure(this.titleLayout);
    this.mTitleElement.setTextSize(SkinManager.getInstance().getMiddleTextSize());
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setNode"))
    {
      ProgramNode localProgramNode = (ProgramNode)paramObject;
      this.mTitleElement.setText(localProgramNode.title);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview_bb.DanmakuPlayInfoView
 * JD-Core Version:    0.6.2
 */