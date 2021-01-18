package com.github.nnnnusui.musicdsl.entity
package score.note

object NoteValue {
  private val _0: NoteValue = Value(0)
  def apply(value: Int*): NoteValue =
    value
      .map(it => Value(it))
      .foldLeft(_0) { (it, sum) => sum + it }

  private case class Value(value: Int) extends NoteValue {
    override def percentage: Double =
      value match {
        case 0  => 0.0
        case it => 1.0 / it
      }
  }
  private case class Values(values: Seq[NoteValue]) extends NoteValue {
    override def percentage: Double = values.map(_.percentage).sum
  }
  private case class HasBase(base: NoteValue, value: NoteValue) extends NoteValue {
    override def percentage: Double = value.percentage * base.percentage
  }
}
sealed trait NoteValue {
  def percentage: Double
  private def base(base: NoteValue): NoteValue = NoteValue.HasBase(base, this)

  def +(rhs: NoteValue): NoteValue = NoteValue.Values(Seq(this, rhs))
  def +(rhs: Int): NoteValue = this + NoteValue(rhs)
  def *(rhs: Int): NoteValue = NoteValue.Values(Seq.fill(rhs)(this))
  def /(rhs: NoteValue): NoteValue = this.base(rhs)
  def /(rhs: Int): NoteValue = this / NoteValue(rhs)
}
