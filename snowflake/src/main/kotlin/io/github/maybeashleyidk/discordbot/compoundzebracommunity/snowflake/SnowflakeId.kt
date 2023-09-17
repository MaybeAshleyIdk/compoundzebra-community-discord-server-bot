package io.github.maybeashleyidk.discordbot.compoundzebracommunity.snowflake

@JvmInline
public value class SnowflakeId private constructor(private val value: ULong) : Comparable<SnowflakeId> {

	public companion object {

		private const val TIMESTAMP_MASK: Long = 0x3FFFFFFFFFFL
		private const val WORKER_ID_MASK: UShort = 0x3FFu
		private const val SEQUENCE_NR_MASK: UShort = 0xFFFu

		private const val TIMESTAMP_SHIFT = 22
		private const val WORKER_ID_SHIFT = 12

		public fun ofULong(uLong: ULong): SnowflakeId {
			return SnowflakeId(uLong)
		}

		public fun fromParts(timestamp: Long, workerId: UShort, sequenceNr: UShort): SnowflakeId {
			val timestampPart: ULong = (timestamp and this.TIMESTAMP_MASK).toULong() shl this.TIMESTAMP_SHIFT
			val workedIdPart: ULong = (workerId and this.WORKER_ID_MASK).toULong() shl this.WORKER_ID_SHIFT
			val sequenceNrPart: ULong = (sequenceNr and this.SEQUENCE_NR_MASK).toULong()

			val value: ULong = timestampPart or workedIdPart or sequenceNrPart
			return this.ofULong(value)
		}
	}

	override fun compareTo(other: SnowflakeId): Int {
		return this.value.compareTo(other.value)
	}

	override fun toString(): String {
		return this.value.toString()
	}
}
