package mjs.processing.mobile.mthinlet;

public class MInvoke
{
	public final Object source;

	public final Object part;

	public final String action;

	public final Object[] args;

	protected MInvoke(Object source, Object part, String action, Object[] args)
	{
		this.source = source;
		this.part = part;
		this.action = action;
		this.args = args;
	}
}